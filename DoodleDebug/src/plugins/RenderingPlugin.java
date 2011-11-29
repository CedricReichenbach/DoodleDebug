package plugins;

import java.util.Set;

import rendering.Rendering;

import doodle.RealScratch;

public interface RenderingPlugin extends Rendering {
	public Set<Class<?>> getDrawableClasses();
}
