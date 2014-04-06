package ch.unibe.scg.doodle.hbase;

import java.net.URL;
import java.util.AbstractMap;
import java.util.AbstractMap.SimpleEntry;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class HBaseIntMap implements Map<Integer, Object> {

	HBaseStringMap stringMap;

	public HBaseIntMap(String tableName) {
		this.stringMap = new HBaseStringMap(tableName);
	}

	@Override
	public void clear() {
		stringMap.clear();
	}

	@Override
	public boolean containsKey(Object key) {
		if (!(key instanceof Integer)) {
			System.err.println("WARNING: HBaseMap key needs to be an integer");
			return false;
		}

		return stringMap.containsKey(key.toString());
	}

	@Override
	public boolean containsValue(Object value) {
		return stringMap.containsValue(value);
	}

	@Override
	public Set<Entry<Integer, Object>> entrySet() {
		HashSet<Entry<Integer, Object>> set = new HashSet<>();

		for (Integer key : keySet()) {
			Object value = get(key);
			SimpleEntry<Integer, Object> entry = new AbstractMap.SimpleEntry<>(
					key, value);
			set.add(entry);
		}

		return set;
	}

	@Override
	public Object get(Object key) {
		if (!(key instanceof Integer)) {
			System.err.println("WARNING: HBaseMap key needs to be an integer");
			return null;
		}

		return stringMap.get(key.toString());
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
	public Object put(Integer key, Object value) {
		return stringMap.put(key.toString(), value);
	}

	@Override
	public void putAll(Map<? extends Integer, ? extends Object> otherMap) {
		for (Integer key : otherMap.keySet())
			this.put(key, otherMap.get(key));
	}

	@Override
	public Object remove(Object key) {
		return stringMap.remove(key.toString());
	}

	@Override
	public int size() {
		return stringMap.size();
	}

	@Override
	public Collection<Object> values() {
		return stringMap.values();
	}

	public void addToClassLoader(URL url) {
		stringMap.addToClassLoader(url);
	}

}
