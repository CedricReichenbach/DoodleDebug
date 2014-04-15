package ch.unibe.scg.doodle;

import java.util.HashSet;
import java.util.Set;

import ch.unibe.scg.doodle.hbase.HBaseIntMap;
import ch.unibe.scg.doodle.helperClasses.Nullable;
import ch.unibe.scg.doodle.typeTransport.ClassManager;
import ch.unibe.scg.doodle.typeTransport.ClassUtil;

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

	private HBaseIntMap<Object> hBaseMap;
	private HBaseIntMap<Set<String>> classesMap;
	private HBaseIntMap<Integer> persistenceMap;
	private static final String TABLE_NAME = "clickables";
	private static final String CLASSNAME_TABLE_NAME = "clickables_classnames";
	private static final String PERSISTENCE_TABLE_NAME = "clickables_persistence";
	private static final int NEXT_ID_KEY = 0;

	private ClassManager classManager;

	public IndexedObjectStorage() {
		this.persistenceMap = new HBaseIntMap<>(PERSISTENCE_TABLE_NAME);

		this.nextID = persistenceMap.containsKey(NEXT_ID_KEY) ? persistenceMap
				.get(NEXT_ID_KEY) : 0;
		this.ringBuffer = new Object[CAPACITY];

		// XXX: Should we really store clickables?
		this.hBaseMap = new HBaseIntMap<>(TABLE_NAME);
		this.classesMap = new HBaseIntMap<>(CLASSNAME_TABLE_NAME);

		classManager = new ClassManager();
	}

	/** @return Id of stored object. */
	public int store(Object o) {
		hBaseMap.put(nextID, o);
		// FIXME: Will store classes everytime an object is doodled
		if (ClassUtil.isThirdParty(o.getClass())) {
			Set<String> nameSet = new HashSet<String>();
			for (Class<?> clazz : ClassUtil.getThirdPartyDependencies(o
					.getClass())) {
				String clazzName = classManager.store(clazz);
				nameSet.add(clazzName);
			}
			classesMap.put(nextID, nameSet);
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
			loadClasses(classesMap.get(id));

		if (id < nextID - CAPACITY || id >= nextID
				|| ringBuffer[id % CAPACITY] == null)
			return hBaseMap.get(id);

		return ringBuffer[id % CAPACITY];
	}

	private void loadClasses(Set<String> classNames) {
		for (String className : classNames)
			classManager.loadToFile(className);
	}

	public boolean hasClassName(int id) {
		return classesMap.containsKey(id);
	}
}
