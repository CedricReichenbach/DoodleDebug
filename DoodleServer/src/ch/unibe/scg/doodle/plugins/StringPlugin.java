package ch.unibe.scg.doodle.plugins;


import java.util.HashSet;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Provider;

import ch.unibe.ch.scg.htmlgen.Tag;
import ch.unibe.scg.doodle.rendering.StringRendering;


public class StringPlugin extends AbstractPlugin {

	@Inject
	Provider<StringRendering> stringRenderingProvider;
	
	@Override
	public Set<Class<?>> getDrawableClasses() {
		HashSet<Class<?>> hs = new HashSet<Class<?>>();
		hs.add(String.class);
		return hs;
	}

	@Override
	public void render(Object string, Tag tag) {
		stringRenderingProvider.get().render((String) string, tag);
	}

}
