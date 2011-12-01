package plugins;

import java.util.Set;

import rendering.Rendering;

import doodle.Scratch;

public interface RenderingPlugin extends Rendering {
	public Set<Class<?>> getDrawableClasses();
}
