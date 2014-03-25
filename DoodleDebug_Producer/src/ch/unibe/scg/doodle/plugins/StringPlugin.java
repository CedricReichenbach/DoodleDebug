package ch.unibe.scg.doodle.plugins;

import java.util.HashSet;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Provider;

import ch.unibe.scg.doodle.htmlgen.Tag;
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

	@Override
	public void renderSimplified(Object string, Tag tag) {
		stringRenderingProvider.get().renderSimplified((String) string, tag);
	}

	@Override
	public String getCSS() {
		String big = ".StringPlugin {padding: 1px;}";
		String small = ".StringPlugin.smallRendering {padding: 0;}";
		String paragraph = ".StringPlugin .StringRendering {margin: 0;}";
		return big + small + paragraph;
	}

}
