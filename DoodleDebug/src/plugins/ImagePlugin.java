package plugins;

import html_generator.Tag;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Provider;

import rendering.ImageRendering;
import rendering.ListRendering;

import doodle.HtmlImage;

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
