package ch.unibe.scg.doodle.database;

import java.util.AbstractMap;
import java.util.AbstractMap.SimpleEntry;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class DatabaseIntMap<T> implements Map<Integer, T> {

	DoodleDatabaseMap<T> stringMap;

	public DatabaseIntMap(String applicationName, String tableName) {
		this.stringMap = DoodleDatabaseMapProvider.get(applicationName, tableName);
	}

	@Override
	public void clear() {
		stringMap.clear();
	}

	@Override
	public boolean containsKey(Object key) {
		if (!(key instanceof Integer)) {
			System.err.println("WARNING: HBaseIntMap key needs to be an integer");
			return false;
		}

		return stringMap.containsKey(key.toString());
	}

	@Override
	public boolean containsValue(Object value) {
		return stringMap.containsValue(value);
	}

	@Override
	public Set<Entry<Integer, T>> entrySet() {
		HashSet<Entry<Integer, T>> set = new HashSet<>();

		for (Integer key : keySet()) {
			T value = get(key);
			SimpleEntry<Integer, T> entry = new AbstractMap.SimpleEntry<>(key,
					value);
			set.add(entry);
		}

		return set;
	}

	@Override
	public T get(Object key) {
		if (!(key instanceof Integer)) {
			System.err.println("WARNING: HBaseIntMap key needs to be an integer");
			return null;
		}

		return (T) stringMap.get(key.toString());
	}

	@Override
	public boolean isEmpty() {
		return stringMap.isEmpty();
	}

	@Override
	public Set<Integer> keySet() {
		Set<Integer> set = new HashSet<>();

		Set<String> sKeys = stringMap.keySet();
		for (String sKey : sKeys)
			set.add(Integer.parseInt(sKey));

		return set;
	}

	@Override
	public T put(Integer key, T value) {
		return stringMap.put(key.toString(), value);
	}

	@Override
	public void putAll(Map<? extends Integer, ? extends T> otherMap) {
		for (Integer key : otherMap.keySet())
			this.put(key, otherMap.get(key));
	}

	@Override
	public T remove(Object key) {
		return stringMap.remove(key.toString());
	}

	@Override
	public int size() {
		return stringMap.size();
	}

	@Override
	public Collection<T> values() {
		return stringMap.values();
	}

}
