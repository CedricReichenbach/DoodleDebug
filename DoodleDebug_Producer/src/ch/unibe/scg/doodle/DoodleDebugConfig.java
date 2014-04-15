package ch.unibe.scg.doodle;

public class DoodleDebugConfig {

	private static boolean readingMode;

	static void setReadingMode(boolean readingMode) {
		DoodleDebugConfig.readingMode = readingMode;
	}

	public static boolean inReadingMode() {
		return DoodleDebugConfig.readingMode;
	}

}
