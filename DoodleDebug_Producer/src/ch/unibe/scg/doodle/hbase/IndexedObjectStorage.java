package ch.unibe.scg.doodle.hbase;

import java.util.HashSet;
import java.util.Set;

import ch.unibe.scg.doodle.DoodleDebugConfig;
import ch.unibe.scg.doodle.helperClasses.Nullable;
import ch.unibe.scg.doodle.typeTransport.ClassUtil;
import ch.unibe.scg.doodle.util.ApplicationUtil;

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

	public IndexedObjectStorage() {
		this.persistenceMap = new HBaseIntMap<>(
				ApplicationUtil.getApplicationName(), PERSISTENCE_TABLE_NAME);

		this.nextID = persistenceMap.containsKey(NEXT_ID_KEY) ? persistenceMap
				.get(NEXT_ID_KEY) : 0;
		this.ringBuffer = new Object[CAPACITY];

		// XXX: Should we really store clickables?
		this.hBaseMap = new HBaseIntMap<>(ApplicationUtil.getApplicationName(),
				TABLE_NAME);
		this.classesMap = new HBaseIntMap<>(
				ApplicationUtil.getApplicationName(), CLASSNAME_TABLE_NAME);
	}

	/** @return Id of stored object. */
	public int store(Object o) {
		hBaseMap.put(nextID, o);
		// FIXME: Will store classes everytime an object is doodled
		// FIXME: What about arrays (containing third-party types)?
		if (ClassUtil.isThirdParty(o.getClass())
				& !DoodleDebugConfig.inReadingMode()) {
			Set<String> nameSet = new HashSet<String>();
			for (Class<?> clazz : ClassUtil.getThirdPartyDependencies(o
					.getClass())) {
				String clazzName = ClassManager.store(clazz);
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
		// XXX: Loads classes multiple times
		// XXX: Classes are not versioned (they might change)
		if (this.hasClassName(id))
			loadClasses(classesMap.get(id));

		if (id < nextID - CAPACITY || id >= nextID
				|| ringBuffer[id % CAPACITY] == null)
			return hBaseMap.get(id);

		return ringBuffer[id % CAPACITY];
	}

	private void loadClasses(Set<String> classNames) {
		for (String className : classNames)
			ClassManager.loadToFile(className);
	}

	public boolean hasClassName(int id) {
		return classesMap.containsKey(id);
	}
}
