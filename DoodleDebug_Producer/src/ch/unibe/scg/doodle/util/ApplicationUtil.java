package ch.unibe.scg.doodle.util;

public class ApplicationUtil {
	
	private static final String UNKNOWN_CLASS = "ch.unibe.scg.doodle.UNKNOWN_APP_PLACEHOLDER";
	
	public static String getMainClassName() {
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
}
