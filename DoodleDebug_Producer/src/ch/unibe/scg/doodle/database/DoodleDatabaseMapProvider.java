package ch.unibe.scg.doodle.database;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import ch.unibe.scg.doodle.database.hbase.HBaseStringMap;

public class DoodleDatabaseMapProvider {

	@SuppressWarnings("rawtypes")
	// eclipse/compiler is not smart enough
	private static Class<? extends DoodleDatabaseMap> mapClass = HBaseStringMap.class;
	private static Map<String, DoodleDatabaseMap<?>> mapCache = new HashMap<>();

	Class<?> x = DoodleDatabaseMap.class;

	@SuppressWarnings("rawtypes")
	public static void setClass(Class<? extends DoodleDatabaseMap> newMapClass) {
		mapClass = newMapClass;
		mapCache = new HashMap<>(); // clear cache
	}

	@SuppressWarnings("unchecked")
	public static <T> DoodleDatabaseMap<T> get(String mapName) {
		if (mapCache.containsKey(mapName))
			return (DoodleDatabaseMap<T>) mapCache.get(mapName);

		try {
			DoodleDatabaseMap<T> map = mapClass.getConstructor(String.class)
					.newInstance(mapName);
			mapCache.put(mapName, map);
			return map;
		} catch (InstantiationException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			throw new RuntimeException(e);
		}
	}

	public static <T> DoodleDatabaseMap<T> get(String applicationName,
			String mapName) {
		MetaInfo.addApplicationName(applicationName);
		return get(applicationName + "-" + mapName);
	}
}
