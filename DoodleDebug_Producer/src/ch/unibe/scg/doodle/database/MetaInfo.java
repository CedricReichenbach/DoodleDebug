package ch.unibe.scg.doodle.database;

import java.util.HashSet;
import java.util.Set;

public class MetaInfo {
	private static String TABLE_FULL_NAME = "meta_info";
	private static String APP_NAMES_KEY = "application_names";
	private static DoodleDatabaseMap<Set<String>> metaInfo = DoodleDatabaseMapFactory
			.get(TABLE_FULL_NAME);

	// XXX: had to make public instead of package scope due to chipping of hbase
	// part
	public static void addApplicationName(String applicationName) {
		Set<String> appNames = getAllApplicationNames();
		if (appNames.contains(applicationName))
			return;

		appNames.add(applicationName);
		metaInfo.put(APP_NAMES_KEY, appNames);
	}

	public static Set<String> getAllApplicationNames() {
		if (!metaInfo.containsKey(APP_NAMES_KEY))
			metaInfo.put(APP_NAMES_KEY, new HashSet<String>());

		return metaInfo.get(APP_NAMES_KEY);
	}
}
