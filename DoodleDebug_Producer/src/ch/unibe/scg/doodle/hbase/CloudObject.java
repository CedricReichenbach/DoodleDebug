package ch.unibe.scg.doodle.hbase;

/**
 * Abstraction layer for objects that should always be in sync with HBase
 * (cloud).
 * 
 * @author cedric
 * 
 */
public class CloudObject<T> {

	private static final String TABLE_NAME = "cloud-objects";
	private final String id;
	private T object;
	private final HBaseStringMap<T> map;

	/**
	 * 
	 * @param object
	 *            The object to be in sync
	 * @param id
	 *            Should be unique among all cloud objects
	 */
	public CloudObject(T object, String id) {
		this.object = object;
		this.id = id;
		this.map = new HBaseStringMap<T>(TABLE_NAME);
		this.save();
	}

	public T get() {
		object = map.get(id);
		return object;
	}
	
	public void save() {
		map.put(id, object);
	}
}
