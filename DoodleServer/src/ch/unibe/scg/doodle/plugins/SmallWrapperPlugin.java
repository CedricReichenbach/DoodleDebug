package ch.unibe.scg.doodle.plugins;

import java.util.HashSet;
import java.util.Set;

import javax.inject.Inject;

import ch.unibe.scg.doodle.helperClasses.SmallWrapper;
import ch.unibe.scg.doodle.htmlgen.Tag;
import ch.unibe.scg.doodle.rendering.DoodleRenderException;
import ch.unibe.scg.doodle.rendering.SmallWrapperRendering;

public class SmallWrapperPlugin extends AbstractPlugin {

	@Inject
	SmallWrapperRendering swRendering;

	@Override
	public Set<Class<?>> getDrawableClasses() {
		HashSet<Class<?>> hs = new HashSet<Class<?>>();
		hs.add(SmallWrapper.class);
		return hs;
	}

	@Override
	public void render(Object object, Tag tag) {
		swRendering.render((SmallWrapper) object, tag);
	}

	@Override
	public void renderSmall(Object object, Tag tag) {
		swRendering.render((SmallWrapper) object, tag);
	}

}
