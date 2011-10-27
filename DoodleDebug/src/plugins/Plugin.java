package plugins;

import java.util.Set;

import doodle.Scratch;

public interface Plugin {
	public Set<Class<?>> getDrawableClasses();
	public void draw(Object o, Scratch s);
}
