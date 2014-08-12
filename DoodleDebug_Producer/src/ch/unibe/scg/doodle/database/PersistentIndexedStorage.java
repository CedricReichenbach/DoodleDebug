package ch.unibe.scg.doodle.database;


class PersistentIndexedStorage {

	private final DatabaseIntMap<Object> hBaseMap;
	/** Entry 0 is used for persistence. */
	private static final int NEXT_ID_KEY = 0;

	private int nextId = 1;

	public PersistentIndexedStorage(String applicationName, String tableName) {
		this.hBaseMap = new DatabaseIntMap<>(applicationName, tableName);

		if (hBaseMap.containsKey(NEXT_ID_KEY))
			nextId = (int) hBaseMap.get(NEXT_ID_KEY);
	}

	/** @return Id of stored object. */
	public int store(Object o) {
		hBaseMap.put(nextId, o);

		hBaseMap.put(NEXT_ID_KEY, ++nextId);
		return nextId - 1;
	}

	public Object load(int index) {
		return hBaseMap.get(index);
	}
}
