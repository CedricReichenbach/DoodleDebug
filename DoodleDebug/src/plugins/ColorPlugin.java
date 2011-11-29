package plugins;

import html_generator.Tag;

import java.awt.Color;
import java.util.HashSet;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Provider;

import rendering.ColorRendering;

public class ColorPlugin implements RenderingPlugin {
	
	@Inject
	Provider<ColorRendering> colorRenderingProvider;

	@Override
	public Set<Class<?>> getDrawableClasses() {
		HashSet<Class<?>> hs = new HashSet<Class<?>>();
		hs.add(Color.class);
		return hs;
	}

	@Override
	public void render(Object color, Tag tag) {
		colorRenderingProvider.get().render((Color) color, tag);
	}

}
