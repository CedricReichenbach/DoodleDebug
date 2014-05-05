package ch.unibe.scg.doodle.util;

public class ApplicationUtil {
	
	// No, don't assign it here (main thread needs to be started first).
	private static String applicationName;
	
	private static final String UNKNOWN_CLASS = "ch.unibe.scg.doodle.UNKNOWN_APP_PLACEHOLDER";
	
	private static String getMainClassName() {
		for (StackTraceElement[] stackTrace : Thread.getAllStackTraces()
				.values()) {
			if (stackTrace.length == 0)
				continue;

			StackTraceElement lastElement = stackTrace[stackTrace.length - 1];
			// XXX: Not bulletproof, but you need criminal intent to break this
			if (lastElement.getMethodName().equals("main"))
				return lastElement.getClassName();
		}
		return UNKNOWN_CLASS;
	}
	
	public static String getApplicationName() {
		if (applicationName == null)
			setApplicationName(getMainClassName());
		return applicationName;
	}
	
	public static void setApplicationName(String newName) {
		applicationName = newName;
	}
}
