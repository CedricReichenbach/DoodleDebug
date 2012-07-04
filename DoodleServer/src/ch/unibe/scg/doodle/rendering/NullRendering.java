package ch.unibe.scg.doodle.rendering;

import ch.unibe.scg.doodle.helperClasses.NullObject;
import ch.unibe.scg.htmlgen.Tag;

public class NullRendering implements Rendering<NullObject> {

	@Override
	public void render(NullObject object, Tag tag) {
		tag.add("null");
	}

}
