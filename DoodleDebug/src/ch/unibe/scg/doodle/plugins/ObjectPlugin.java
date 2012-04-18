package ch.unibe.scg.doodle.plugins;


import java.util.HashSet;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Provider;

import ch.unibe.ch.scg.htmlgen.Tag;
import ch.unibe.scg.doodle.rendering.DefaultRendering;


/**
 * Represents default drawing of any Object, if no other is defined.
 * @author Cedric Reichenbach
 *
 */
public class ObjectPlugin extends AbstractPlugin {
	final Provider<DefaultRendering> defaultRenderingProvider;
	
	@Inject
	public ObjectPlugin(Provider<DefaultRendering> defaultRenderingProvider) {
		super();
		this.defaultRenderingProvider = defaultRenderingProvider;
	}

	
	

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
