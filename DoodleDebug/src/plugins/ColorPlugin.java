package plugins;

import html_generator.Tag;

import java.awt.Color;
import java.util.HashSet;
import java.util.Set;

import rendering.ColorRendering;

public class ColorPlugin implements RenderingPlugin {

	@Override
	public Set<Class<?>> getDrawableClasses() {
		HashSet<Class<?>> hs = new HashSet<Class<?>>();
		hs.add(Color.class);
		return hs;
	}

	@Override
	public void render(Object color, Tag tag) {
		new ColorRendering().render((Color) color, tag);
	}

}
