package ch.unibe.scg.doodle.rendering;

import java.awt.Image;
import java.io.IOException;

import javax.inject.Inject;

import ch.unibe.scg.doodle.ImageManager;
import ch.unibe.scg.doodle.htmlgen.Tag;

public class ImageRendering implements Rendering<Image> {

	@Inject
	ImageManager imageManager;

	@SuppressWarnings("unchecked")
	@Override
	public void render(Image image, Tag tag) throws DoodleRenderException {
		Tag imgTag = new Tag("img");
		try {
			imgTag.addAttribute("data-image-id", imageManager.store(image) + "");
		} catch (IOException e) {
			imgTag.addCSSClass("error");
		}
		tag.add(imgTag);
	}

	@Override
	public void renderSimplified(Image image, Tag tag)
			throws DoodleRenderException {
		this.render(image, tag); // different css only
	}

}
