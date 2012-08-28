package ch.unibe.scg.doodle.api;

import ch.unibe.scg.doodle.Doodler;
import ch.unibe.scg.doodle.htmlgen.Tag;

public class DoodleRenderer {

	/**
	 * (Sub-)Renders any object into the provided html tag. The rendered object
	 * will be clickable/inspectable in the output.
	 * 
	 * @param object
	 * @param tag
	 * @param showClassName
	 */
	public static void renderInto(Object object, Tag tag, boolean showClassName) {
		if (showClassName)
			Doodler.instance().renderInlineInto(object, tag);
		else
			Doodler.instance().renderInlineIntoWithoutClassName(object, tag);
	}
}
