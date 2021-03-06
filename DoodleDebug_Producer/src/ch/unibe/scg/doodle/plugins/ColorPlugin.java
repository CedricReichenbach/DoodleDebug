package ch.unibe.scg.doodle.plugins;

import java.awt.Color;
import java.util.HashSet;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Provider;

import ch.unibe.scg.doodle.htmlgen.Tag;
import ch.unibe.scg.doodle.rendering.ColorRendering;

public class ColorPlugin extends AbstractPlugin {

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

	@Override
	public void renderSimplified(Object color, Tag tag) {
		colorRenderingProvider.get().renderSimplified((Color) color, tag);
	}

	@Override
	public String getCSS() {
		String main = ".ColorPlugin {padding:2px; background: none;} ";
		String small = ".ColorPlugin.smallRendering {padding:4px}";
		String dark = ".ColorPlugin.darkColor {color: white;}";
		String text = ".ColorPlugin .colorText {margin-bottom: 2px;}";
		String parts = ".ColorPlugin .redPart {border-bottom: 2px solid red;}"
				+ ".ColorPlugin .greenPart {border-bottom: 2px solid green;}"
				+ ".ColorPlugin .bluePart {border-bottom: 2px solid blue;}"
				+ ".ColorPlugin .alphaPart {border-bottom: 2px solid black;}"
				+ ".ColorPlugin.darkColor .alphaPart {border-bottom: 2px solid white;}";
		return main + small + dark + text + parts;
	}

}
