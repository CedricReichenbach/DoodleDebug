package ch.unibe.scg.doodle.plugins;

import java.util.HashSet;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Provider;

import ch.unibe.scg.doodle.htmlgen.Tag;
import ch.unibe.scg.doodle.rendering.BooleanRendering;

public class BooleanPlugin extends AbstractPlugin {

	@Inject
	Provider<BooleanRendering> booleanRenderingProvider;

	@Override
	public Set<Class<?>> getDrawableClasses() {
		HashSet<Class<?>> hs = new HashSet<Class<?>>();
		hs.add(Boolean.class);
		return hs;
	}

	@Override
	public void render(Object bool, Tag tag) {
		booleanRenderingProvider.get().render((Boolean) bool, tag);
	}

	@Override
	public void renderSmall(Object bool, Tag tag) {
		booleanRenderingProvider.get().renderSmall((Boolean) bool, tag);
	}

	@Override
	public String getCSS() {
		String bool = ".BooleanPlugin .boolean {}";
		String small = ".BooleanPlugin.smallRendering .boolean * { padding: 0.5em; border-radius: 0.5em;}";
		String trueCSS = ".BooleanPlugin .boolean .true {box-shadow: 0 0 1em #0e0 inset;}";
		String falseCSS = ".BooleanPlugin .boolean .false {box-shadow: 0 0 1em #f22 inset;}";
		return bool + small + trueCSS + falseCSS;
	}

}
