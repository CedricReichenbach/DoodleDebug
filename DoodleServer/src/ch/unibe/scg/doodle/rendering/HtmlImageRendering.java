package ch.unibe.scg.doodle.rendering;

import ch.unibe.scg.doodle.helperClasses.HtmlImage;
import ch.unibe.scg.doodle.htmlgen.Tag;

public class HtmlImageRendering implements Rendering<HtmlImage> {

	@SuppressWarnings("unchecked")
	@Override
	public void render(HtmlImage image, Tag tag) {
		tag.add(image.asHtml());
	}

}
