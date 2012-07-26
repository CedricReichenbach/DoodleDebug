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
	 * Clear the output tab and leave a blank page. Suggestion: Use this method
	 * very carefully since the history is cleared anyway at the beginning of
	 * each run.
	 */
	public static void clearHistory() {
		DoodleClient.instance().clearOutput();
	}

	/**
	 * Includes all Console inputs into DoodleDebug output
	 * 
	 * @deprecated WARNING: Not implemented yet!
	 */
	public static void includeSystemOut() {
		// XXX Problem: To catch everything, you have to implement an
		// OutputStream which gets each character one after another...
	}
}
