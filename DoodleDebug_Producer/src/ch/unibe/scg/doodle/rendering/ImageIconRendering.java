package ch.unibe.scg.doodle.rendering;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.swing.ImageIcon;

import ch.unibe.scg.doodle.htmlgen.Tag;

public class ImageIconRendering implements Rendering<ImageIcon> {

	@Inject
	Provider<ImageRendering> imageRenderingProvider;

	@Override
	public void render(ImageIcon icon, Tag tag) throws DoodleRenderException {
		imageRenderingProvider.get().render(icon.getImage(), tag);
	}
	
	@Override
	public void renderSimplified(ImageIcon icon, Tag tag) throws DoodleRenderException {
		this.render(icon, tag); // different css only
	}

}
