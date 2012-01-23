package plugins;

import html_generator.Tag;

import java.util.HashSet;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Provider;

import rendering.StringRendering;

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
