package ch.unibe.scg.doodle.properties;

import java.io.File;
import java.util.Properties;

public class DoodleDebugProperties {

	private static Properties properties;

	private static Properties properties() {
		if (properties == null)
			properties = PropertiesUtil.getDoodleDebugProperties();
		return properties;
	}

	/**
	 * Class intended to be static only.
	 */
	private DoodleDebugProperties() {

	}

	public static boolean betaMode() {
		return checkIfTrue("beta");
	}

	public static String getFeedbackMailAddress() {
		return properties().getProperty("feedbackMail");
	}

	public static boolean developMode() {
		return checkIfTrue("developMode");
	}

	public static boolean openInBrowser() {
		return checkIfTrue("openInBrowser");
	}

	private static boolean checkIfTrue(String key) {
		return properties().getProperty(key).equals("true");
	}

	public static File tempFileForOutput() {
		// Win7: C:\Users\<user>\AppData\Local\Temp\doodledebug\output.html
		File file = new File(tempDir() + "/output.html");
		file.mkdirs();
		return file;
	}

	public static File tempDirForImages() {
		File dir = new File(tempDir() + "/img/");
		dir.mkdirs();
		return dir;
	}
	
	public static File mainTempDir() {
		File dir = new File(tempDir());
		dir.mkdirs();
		return dir;
	}

	private static String tempDir() {
		return System.getProperty("java.io.tmpdir") + "/doodleDebug";
	}
}
