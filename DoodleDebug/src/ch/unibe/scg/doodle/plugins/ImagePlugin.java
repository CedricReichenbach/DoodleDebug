package ch.unibe.scg.doodle.plugins;


import java.util.HashSet;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Provider;

import ch.unibe.ch.scg.htmlgen.Tag;
import ch.unibe.scg.doodle.helperClasses.HtmlImage;
import ch.unibe.scg.doodle.rendering.ImageRendering;

public class ImagePlugin extends AbstractPlugin {

	@Inject
	Provider<ImageRendering> imageRenderingProvider;
	
	@Override
	public Set<Class<?>> getDrawableClasses() {
		HashSet<Class<?>> hs = new HashSet<Class<?>>();
		hs.add(HtmlImage.class);
		return hs;
	}

	@Override
	public void render(Object htmlImage, Tag tag) {
		imageRenderingProvider.get().render((HtmlImage) htmlImage, tag);
	}

}
