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
	static final int CAPACITY = 2;//XXX 10000;
	private final Object[] ringBuffer;
	private int nextID; // TODO: Use long to prevent overflow

	private HBaseMap hBaseMap;
	private static final String TABLE_NAME = "clickables";

	public IndexedObjectStorage() {
		this.nextID = 0;
		this.ringBuffer = new Object[CAPACITY];
		
		// XXX: Should we really store clickables?
		this.hBaseMap = new HBaseMap(TABLE_NAME);
	}

	/** @return Id of stored object. */
	public int store(Object o) {
		hBaseMap.put(nextID, o);
		
		ringBuffer[nextID % CAPACITY] = o;
		return nextID++;
	}

	public @Nullable Object get(int id) {
		if (id < nextID - CAPACITY || id >= nextID)
			return hBaseMap.get(id);
		
		return ringBuffer[id % CAPACITY];
	}
}
