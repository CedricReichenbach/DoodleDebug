package ch.unibe.scg.doodle.hbase;

import java.util.Set;

public class MetaInfo {
	private static String TABLE_FULL_NAME = "meta_info";
	private static String APP_NAMES_KEY = "application_names";
	private static HBaseStringMap<Set<String>> metaInfo = new HBaseStringMap<Set<String>>(
			TABLE_FULL_NAME);
	
	static void addApplicationName(String applicationName) {
		Set<String> appNames = getAllApplicationNames();
		if (appNames.contains(applicationName))
			return;
		
		appNames.add(applicationName);
		metaInfo.put(APP_NAMES_KEY, appNames);
	}

	public static Set<String> getAllApplicationNames() {
		return metaInfo.get(APP_NAMES_KEY);
	}
}
