package ch.unibe.scg.doodle.plugins;

import java.util.Set;

import ch.unibe.scg.doodle.rendering.Rendering;

public interface RenderingPlugin extends Rendering {

	public Set<Class<?>> getDrawableClasses();

	public String getClassAttribute();

	public String getObjectTypeName(Object o);

	/**
	 * Allow this RenderingPlugin to provide CSS rules.
	 * 
	 * @return Rules as String, should be valid CSS
	 */
	public String getCSS();
}
