package ch.unibe.scg.doodle.plugins;

import java.util.HashSet;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Provider;

import ch.unibe.scg.doodle.htmlgen.Tag;
import ch.unibe.scg.doodle.rendering.BooleanRendering;
import ch.unibe.scg.doodle.view.fonts.FontUtil;

public class BooleanPlugin extends AbstractPlugin {

	@Inject
	Provider<BooleanRendering> booleanRenderingProvider;

	@Override
	public Set<Class<?>> getDrawableClasses() {
		HashSet<Class<?>> hs = new HashSet<Class<?>>();
		hs.add(Boolean.class);
		return hs;
	}

	@Override
	public void render(Object bool, Tag tag) {
		booleanRenderingProvider.get().render((Boolean) bool, tag);
	}

	@Override
	public void renderSmall(Object bool, Tag tag) {
		booleanRenderingProvider.get().renderSmall((Boolean) bool, tag);
	}

	@Override
	public String getCSS() {
		String bool = ".BooleanPlugin .boolean {padding: 1px; text-align: center;}";
		String font = "@font-face { font-family: \"openSymbol\"; src: url(\""
				+ FontUtil.getFontFile("openSymbol.ttf") + "\")}";
		String small = ".BooleanPlugin.smallRendering .boolean "
				+ "{ width: 1em; height: 1em; border-radius: 0.5em; font-family: \"openSymbol\", \"Roboto\";}";
		String trueCSS = ".BooleanPlugin .true {box-shadow: 0 0 1em #0e0 inset;}";
		String trueSmall = ".BooleanPlugin.smallRendering .true {color: green; box-shadow: none;}";
		String falseCSS = ".BooleanPlugin .false {box-shadow: 0 0 1em #f22 inset;}";
		String falseSmall = ".BooleanPlugin.smallRendering .false "
				+ "{color: #d00; box-shadow: none; position: relative; top: -1pt;}";
		return bool + font + small + trueCSS + trueSmall + falseCSS
				+ falseSmall;
	}

}
