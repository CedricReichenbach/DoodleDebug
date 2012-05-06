package ch.unibe.scg.doodle.rendering;

import ch.unibe.ch.scg.htmlgen.Tag;
import ch.unibe.scg.doodle.helperClasses.NullObject;

public class NullRendering implements Rendering<NullObject> {

	@Override
	public void render(NullObject object, Tag tag) {
		// intentionally left blank
	}

}
