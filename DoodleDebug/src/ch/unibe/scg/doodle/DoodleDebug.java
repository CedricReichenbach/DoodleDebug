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
	 * Clear the output tab and leave a blank page. Suggestion: Use this method very carefully since
	 * the history is cleared anyway at the beginning of each run.
	 */
	public static void clearHistory() {
		DoodleClient.instance().clearOutput();
	}
	
	/**
	 * Includes all Console inputs into DoodleDebug output
	 */
	public static void includeSystemOut() {
		// TODO
	}
}
