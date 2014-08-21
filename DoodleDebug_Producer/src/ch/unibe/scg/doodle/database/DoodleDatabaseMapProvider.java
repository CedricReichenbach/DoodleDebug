package ch.unibe.scg.doodle.database;

import java.lang.reflect.InvocationTargetException;

import ch.unibe.scg.doodle.database.hbase.HBaseStringMap;

public class DoodleDatabaseMapProvider {

	@SuppressWarnings("rawtypes")
	// eclipse/compiler is not smart enough
	private static Class<? extends DoodleDatabaseMap> mapClass = HBaseStringMap.class;

	Class<?> x = DoodleDatabaseMap.class;

	@SuppressWarnings("rawtypes")
	public static void setClass(Class<? extends DoodleDatabaseMap> newMapClass) {
		mapClass = newMapClass;
	}

	@SuppressWarnings("unchecked")
	public static <T> DoodleDatabaseMap<T> get(String mapName) {
		try {
			return mapClass.getConstructor(String.class).newInstance(mapName);
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
