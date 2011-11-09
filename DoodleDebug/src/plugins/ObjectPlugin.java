package plugins;

import java.util.HashSet;
import java.util.Set;

import rendering.DefaultRendering;

import view.DoodleCanvas;

import doodle.Scratch;

/**
 * Represents default drawing of any Object, if no other is defined.
 * @author Cedric Reichenbach
 *
 */
public class ObjectPlugin implements RenderingPlugin {

	@Override
	public Set<Class<?>> getDrawableClasses() {
		HashSet<Class<?>> hs = new HashSet<Class<?>>(1);
		hs.add(Object.class);
		return hs;
	}

	@Override
	public void render(Object object, DoodleCanvas canvas) {
		new DefaultRendering().render(object, canvas);
	}


}
