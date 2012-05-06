package ch.unibe.scg.doodle.rendering;

import ch.unibe.ch.scg.htmlgen.Tag;
import ch.unibe.scg.doodle.helperClasses.HtmlImage;

public class ImageRendering implements Rendering<HtmlImage> {

	@SuppressWarnings("unchecked")
	@Override
	public void render(HtmlImage image, Tag tag) {
		tag.add(image.asHtml());
	}

}
