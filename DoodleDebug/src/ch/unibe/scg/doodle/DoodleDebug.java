package ch.unibe.scg.doodle;

/**
 * This Class contains methods for controlling the output of DoodleDebug. It's
 * intended for users only.
 * 
 * @author Cedric Reichenbach
 * 
 */
public class DoodleDebug {
	/**
	 * Clear the output tab and leave a blank page.
	 */
	public static void clearHistory() {
		DoodleClient.instance().clearOutput();
	}
}
