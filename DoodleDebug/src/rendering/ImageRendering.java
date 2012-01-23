package rendering;

import doodle.HtmlImage;
import html_generator.Tag;

public class ImageRendering implements Rendering<HtmlImage> {

	@SuppressWarnings("unchecked")
	@Override
	public void render(HtmlImage image, Tag tag) {
		tag.add(image.asHtml());
	}

}
