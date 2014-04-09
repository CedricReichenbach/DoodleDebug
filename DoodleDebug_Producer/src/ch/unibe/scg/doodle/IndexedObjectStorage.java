package ch.unibe.scg.doodle;

import ch.unibe.scg.doodle.hbase.HBaseIntMap;
import ch.unibe.scg.doodle.helperClasses.Nullable;
import ch.unibe.scg.doodle.typeTransport.ClassManager;
import ch.unibe.scg.doodle.util.ClassUtil;

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

	private HBaseIntMap hBaseMap;
	private HBaseIntMap classesMap;
	private HBaseIntMap persistenceMap;
	private static final String TABLE_NAME = "clickables";
	private static final String CLASSNAME_TABLE_NAME = "clickables_classnames";
	private static final String PERSISTENCE_TABLE_NAME = "clickables_persistence";
	private static final int NEXT_ID_KEY = 0;

	private ClassManager classManager;

	public IndexedObjectStorage() {
		this.persistenceMap = new HBaseIntMap(PERSISTENCE_TABLE_NAME);

		this.nextID = persistenceMap.containsKey(NEXT_ID_KEY) ? (int) persistenceMap
				.get(NEXT_ID_KEY) : 0;
		this.ringBuffer = new Object[CAPACITY];

		// XXX: Should we really store clickables?
		this.hBaseMap = new HBaseIntMap(TABLE_NAME);
		this.classesMap = new HBaseIntMap(CLASSNAME_TABLE_NAME);

		classManager = new ClassManager();
	}

	/** @return Id of stored object. */
	public int store(Object o) {
		hBaseMap.put(nextID, o);
		// FIXME: Will store class everytime an object is doodled
		if (ClassUtil.isThirdParty(o.getClass())) {
			String name = classManager.store(o.getClass());
			classesMap.put(nextID, name);
		}

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
		if (this.hasClassName(id))
			loadClass(this.getClassName(id));

		if (id < nextID - CAPACITY || id >= nextID
				|| ringBuffer[id % CAPACITY] == null)
			return hBaseMap.get(id);

		return ringBuffer[id % CAPACITY];
	}

	private void loadClass(String className) {
		classManager.loadToFile(className);
	}

	public boolean hasClassName(int id) {
		return classesMap.containsKey(id);
	}

	public String getClassName(int id) {
		return (String) classesMap.get(id);
	}
}
