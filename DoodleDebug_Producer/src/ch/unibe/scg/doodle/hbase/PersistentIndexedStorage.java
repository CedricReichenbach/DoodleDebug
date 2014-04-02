package ch.unibe.scg.doodle.hbase;

public class PersistentIndexedStorage {

	private final HBaseMap hBaseMap;
	/** Entry 0 is used for persistence. */
	private static final int NEXT_ID_KEY = 0;

	private int nextId = 1;

	public PersistentIndexedStorage(String tableName) {
		this.hBaseMap = new HBaseMap(tableName);

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
