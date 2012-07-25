package ch.unibe.scg.doodle.plugins;

import java.awt.Image;
import java.util.HashSet;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Provider;

import ch.unibe.scg.doodle.helperClasses.HtmlImage;
import ch.unibe.scg.doodle.htmlgen.Tag;
import ch.unibe.scg.doodle.rendering.HtmlImageRendering;
import ch.unibe.scg.doodle.rendering.ImageRendering;

public class ImagePlugin extends AbstractPlugin {

	@Inject
	Provider<HtmlImageRendering> htmlImageRenderingProvider;
	@Inject
	Provider<ImageRendering> imageRenderingProvider;

	@Override
	public Set<Class<?>> getDrawableClasses() {
		HashSet<Class<?>> hs = new HashSet<Class<?>>();
		hs.add(HtmlImage.class);
		hs.add(Image.class);
		return hs;
	}

	@Override
	public void render(Object image, Tag tag) {
		if (image instanceof HtmlImage)
			htmlImageRenderingProvider.get().render((HtmlImage) image, tag);
		else if (image instanceof Image)
			imageRenderingProvider.get().render((Image) image, tag);
		else {
			Tag error = new Tag("div","class=error");
			error.add("Could not render image");
			tag.add(error);
		}
	}
}
