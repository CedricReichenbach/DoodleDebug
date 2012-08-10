package ch.unibe.scg.doodle.rendering;

import javax.inject.Inject;

import ch.unibe.scg.doodle.Doodler;
import ch.unibe.scg.doodle.helperClasses.SmallWrapper;
import ch.unibe.scg.doodle.htmlgen.Tag;

public class SmallWrapperRendering implements Rendering<SmallWrapper> {

	@Inject
	Doodler doodler;

	@Override
	public void render(SmallWrapper wrapper, Tag tag) {
		doodler.renderSmallInlineInto(wrapper.object(), tag);
	}

	@Override
	public void renderSmall(SmallWrapper wrapper, Tag tag) {
		doodler.renderSmallInlineInto(wrapper.object(), tag);
	}

}
