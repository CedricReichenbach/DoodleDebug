package ch.unibe.scg.doodle.database;

import java.util.AbstractMap;
import java.util.AbstractMap.SimpleEntry;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Frame for classes implementing a database connection for DoodleDebug. It is
 * crucial that <code>mapName</code> is used for grouping data, i.e. several
 * instances of this class will be created with different mapNames (similar to
 * database tables).
 * 
 * @author Cedric Reichenbach
 *
 * @param <T>
 */
public abstract class DoodleDatabaseMap<T> implements Map<String, T> {

	protected String mapName;

	public DoodleDatabaseMap(String mapName) {
		this.mapName = mapName;
	}

	@Override
	public boolean containsKey(Object key) {
		return this.get(key) != null;
	}

	@Override
	public boolean containsValue(Object value) {
		return values().contains(value);
	}

	@Override
	public Set<Entry<String, T>> entrySet() {
		HashSet<Entry<String, T>> set = new HashSet<>();

		for (String key : keySet()) {
			T value = get(key);
			SimpleEntry<String, T> entry = new AbstractMap.SimpleEntry<>(key,
					value);
			set.add(entry);
		}

		return set;
	}

	@Override
	public boolean isEmpty() {
		return this.size() == 0;
	}

	@Override
	public void putAll(Map<? extends String, ? extends T> otherMap) {
		for (String key : otherMap.keySet())
			this.put(key, otherMap.get(key));
	}

}
