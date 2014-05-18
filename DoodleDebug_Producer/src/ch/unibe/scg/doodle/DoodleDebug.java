package ch.unibe.scg.doodle;

import java.util.Collection;

import ch.unibe.scg.doodle.plugins.AbstractPlugin;
import ch.unibe.scg.doodle.plugins.RenderingPlugin;
import ch.unibe.scg.doodle.rendering.RenderingRegistry;
import ch.unibe.scg.doodle.util.ApplicationUtil;

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
	 * recommended.
	 * 
	 * @param plugins
	 */
	public static void addRenderingPlugins(Collection<RenderingPlugin> plugins) {
		// FIXME: Providing plugin classes (instead of objects) would probably make more sense.
		// FIXME: A Set would make more sense than a Collection
		RenderingRegistry.addPlugins(plugins);
	}

	/**
	 * Overrides automatically detected application name used to distinguish
	 * virtual DoodleDebug "logs". Default is the main method's full class name
	 * (e.g. <code>ch.unibe.scg.myapp.Launcher</code>). <br>
	 * <br>
	 * <em>This method should be called before any Doo.dle calls to assure consistency.</em>
	 * 
	 * @param applicationName
	 */
	public static void setApplicationName(String applicationName) {
		ApplicationUtil.setApplicationName(applicationName);
	}
}
