package ch.unibe.scg.doodle.plugins;

import java.util.Set;

/**
 * Interface extending {@link Rendering} and adding methods for plugins for
 * defining custom visualizations. Users are advised to use
 * {@link AbstractPlugin}.
 * 
 * @author Cedric Reichenbach
 * 
 */
public interface RenderingPlugin extends Rendering {

	public Set<Class<?>> getDrawableClasses();

	/**
	 * Returns the html class attribute for CSS.
	 * 
	 * @return
	 */
	public String getClassAttribute();

	/**
	 * Provide a keyword (html class attribute) for small renderings (when using
	 * renderSmall()).
	 * 
	 * @return
	 */
	public String getSmallCSSKeyword();

	/**
	 * Provide a name to be displayed beside the rendering.
	 * 
	 * @param o
	 * @return
	 */
	public String getObjectTypeName(Object o);

	/**
	 * Allow this RenderingPlugin to provide CSS rules.
	 * 
	 * @return Rules as String, should be valid CSS
	 */
	public String getCSS();
}
