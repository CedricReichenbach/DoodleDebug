package ch.unibe.scg.doodle;

import com.google.inject.Module;

/**
 * This Class contains methods for controlling the output of DoodleDebug. It's
 * intended for users only.
 * 
 * @author Cedric Reichenbach
 * 
 */
public class DoodleDebug {
	
	/**
	 * Add custom modules containing information for custom type renderings.
	 * This method should preferably be called at the beginning of an application, i.e. before the first {@link Doo#dle(Object) Doo.dle()} call.
	 * @param modules
	 */
	public static void addModule(Module... modules) {
		
	}
	
	/**
	 * Clear the output tab and leave a blank page. Suggestion: Use this method
	 * very carefully since the history is cleared anyway at the beginning of
	 * each run.
	 */
	public static void clearHistory() {
		DoodleClient.instance().clearOutput();
	}
}
