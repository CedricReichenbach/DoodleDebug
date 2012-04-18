package ch.unibe.scg.doodle.plugins;

import java.util.Set;

import ch.unibe.scg.doodle.RealScratch;
import ch.unibe.scg.doodle.rendering.Rendering;



public interface RenderingPlugin extends Rendering {
	public Set<Class<?>> getDrawableClasses();

	public String getClassAttribute();
	
	public String getObjectTypeName(Object o);
}
