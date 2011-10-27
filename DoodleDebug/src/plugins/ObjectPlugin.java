package plugins;

import java.util.HashSet;
import java.util.Set;

import doodle.Scratch;

public class ObjectPlugin implements Plugin {

	@Override
	public Set<Class<?>> getDrawableClasses() {
		HashSet<Class<?>> hs = new HashSet<Class<?>>(1);
		hs.add(Object.class);
		return hs;
	}

	@Override
	public void draw(Object o, Scratch s) {
		// TODO Auto-generated method stub

	}

}
