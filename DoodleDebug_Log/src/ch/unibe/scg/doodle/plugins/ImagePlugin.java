package ch.unibe.scg.doodle.plugins;

import java.awt.Image;
import java.util.HashSet;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.swing.ImageIcon;

import ch.unibe.scg.doodle.htmlgen.Tag;
import ch.unibe.scg.doodle.rendering.DoodleRenderException;
import ch.unibe.scg.doodle.rendering.ImageIconRendering;
import ch.unibe.scg.doodle.rendering.ImageRendering;

public class ImagePlugin extends AbstractPlugin {

	@Inject
	Provider<ImageRendering> imageRenderingProvider;
	@Inject
	Provider<ImageIconRendering> imageIconRenderingProvider;

	@Override
	public Set<Class<?>> getDrawableClasses() {
		HashSet<Class<?>> hs = new HashSet<Class<?>>();
		hs.add(Image.class);
		hs.add(ImageIcon.class);
		return hs;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void render(Object image, Tag tag) throws DoodleRenderException {
		if (image instanceof Image)
			imageRenderingProvider.get().render((Image) image, tag);
		else if (image instanceof ImageIcon)
			imageIconRenderingProvider.get().render((ImageIcon) image, tag);
		else {
			Tag error = new Tag("div", "class=error");
			error.add("Could not render image");
			tag.add(error);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void renderSimplified(Object image, Tag tag)
			throws DoodleRenderException {
		if (image instanceof Image)
			imageRenderingProvider.get().renderSimplified((Image) image, tag);
		else if (image instanceof ImageIcon)
			imageIconRenderingProvider.get().renderSimplified(
					(ImageIcon) image, tag);
		else {
			Tag error = new Tag("div", "class=error");
			error.add("Could not render image");
			tag.add(error);
		}
	}

	@Override
	public String getCSS() {
		return ".ImagePlugin img " + "{ max-height: 120px; max-width: 120px;} "
				+ ".ImagePlugin.smallRendering img "
				+ "{ max-height: 40px; max-width: 40px;}";
	}
}
