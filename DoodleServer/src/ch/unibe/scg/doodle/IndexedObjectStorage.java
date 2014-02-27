package ch.unibe.scg.doodle;

import ch.unibe.scg.doodle.hbase.HBaseMap;
import ch.unibe.scg.doodle.helperClasses.Nullable;

/**
 * Storage for objects to be possibly rendered later (clickables). Every stored
 * object maps exactly to one ID.
 * 
 * @author Cedric Reichenbach
 * 
 */
public final class IndexedObjectStorage {
	static final int CAPACITY = 10000;
	private final Object[] ringBuffer;
	private int nextID; // TODO: Use long to prevent overflow

	private HBaseMap hBaseMap;
	private HBaseMap persistenceMap;
	private static final String TABLE_NAME = "clickables";
	private static final String PERSISTENCE_TABLE_NAME = "clickables_persistence";
	private static final int NEXT_ID_KEY = 0;

	public IndexedObjectStorage() {
		this.persistenceMap = new HBaseMap(PERSISTENCE_TABLE_NAME);

		this.nextID = persistenceMap.containsKey(NEXT_ID_KEY) ? (int) persistenceMap
				.get(NEXT_ID_KEY) : 0;
		this.ringBuffer = new Object[CAPACITY];

		// XXX: Should we really store clickables?
		if (DoodleDebugConfig.CLUSTER_MODE)
			this.hBaseMap = new HBaseMap(TABLE_NAME);
	}

	/** @return Id of stored object. */
	public int store(Object o) {
		if (DoodleDebugConfig.CLUSTER_MODE)
			hBaseMap.put(nextID, o);

		ringBuffer[nextID % CAPACITY] = o;
		increaseNextID();
		return nextID - 1;
	}

	private void increaseNextID() {
		nextID++;
		persistenceMap.put(NEXT_ID_KEY, (Integer) nextID);
	}

	public @Nullable
	Object get(int id) {
		if (id < nextID - CAPACITY || id >= nextID)
			return DoodleDebugConfig.CLUSTER_MODE ? hBaseMap.get(id) : null;

		return ringBuffer[id % CAPACITY];
	}
}
