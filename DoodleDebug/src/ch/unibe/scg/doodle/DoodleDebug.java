package ch.unibe.scg.doodle;

import java.util.Collection;
import java.util.Set;

import ch.unibe.scg.doodle.plugins.AbstractPlugin;
import ch.unibe.scg.doodle.plugins.RenderingPlugin;

/**
 * This Class contains methods for controlling the output of DoodleDebug. It's
 * intended for users only.
 * 
 * @author Cedric Reichenbach
 * 
 */
public class DoodleDebug {

	/**
	 * Add custom plugins containing information for custom type renderings. For
	 * creating such a plugin, inheritance of {@link AbstractPlugin} is
	 * recommended.<br>
	 * <strike>This method should preferably be called at the beginning of an
	 * application, i.e. before the first {@link Doo#dle(Object) Doo.dle()}
	 * call.</strike>
	 * 
	 * @param plugins
	 */
	public static void addRenderingPlugins(Collection<RenderingPlugin> plugins) {
		DoodleClient.instance().addPlugins(plugins);
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
