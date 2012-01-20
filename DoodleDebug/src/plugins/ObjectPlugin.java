package plugins;

import html_generator.Tag;

import java.util.HashSet;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Provider;

import rendering.ArrayRendering;
import rendering.DefaultRendering;

import view.DoodleCanvas;

import doodle.RealScratch;

/**
 * Represents default drawing of any Object, if no other is defined.
 * @author Cedric Reichenbach
 *
 */
public class ObjectPlugin extends AbstractPlugin {
	
	@Inject
	Provider<DefaultRendering> defaultRenderingProvider;

	@Override
	public Set<Class<?>> getDrawableClasses() {
		HashSet<Class<?>> hs = new HashSet<Class<?>>(1);
		hs.add(Object.class);
		return hs;
	}

	@Override
	public void render(Object object, Tag tag) {
		defaultRenderingProvider.get().render(object, tag);
	}


}
