package ch.unibe.scg.doodle;

import java.util.Collection;

import ch.unibe.scg.doodle.plugins.AbstractPlugin;
import ch.unibe.scg.doodle.plugins.RenderingPlugin;

/**
 * This Class contains methods for controlling the output of DoodleDebug. It's
 * intended for users only.
 * 
 * @author Cedric Reichenbach
 * 
 */
public class DoodleDebug { // Don't delete this, it's needed here to be doodleable.

	/**
	 * Add custom plugins containing information for custom type renderings. For
	 * creating such a plugin, inheritance of {@link AbstractPlugin} is
	 * recommended.
	 * 
	 * @param plugins
	 */
	public static void addRenderingPlugins(Collection<RenderingPlugin> plugins) {
//		DoodleClient.instance().addPlugins(plugins);
	}
}
