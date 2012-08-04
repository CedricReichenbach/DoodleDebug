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
		String font = getFontFace();
		String small = ".BooleanPlugin.smallRendering .boolean "
				+ "{ width: 1em; height: 1em; border-radius: 0.5em; font-family: \"openSymbolRegular\", \"Roboto\";}";
		String trueCSS = ".BooleanPlugin .true {box-shadow: 0 0 1em #0e0 inset;}";
		String trueSmall = ".BooleanPlugin.smallRendering .true {color: green; box-shadow: none;}";
		String falseCSS = ".BooleanPlugin .false {box-shadow: 0 0 1em #f22 inset;}";
		String falseSmall = ".BooleanPlugin.smallRendering .false "
				+ "{color: #d00; box-shadow: none; position: relative; top: -1pt;}";
		return bool + font + small + trueCSS + trueSmall + falseCSS
				+ falseSmall;
	}

	String getFontFace() {
		return "@font-face { " // font-squirrel
				+ "font-family: 'OpenSymbolRegular'; "
				+ "src: url('opensymbol-webfont.eot'); "
				+ "src: url('"
				+ FontUtil.getFontFileURL("opensymbol-webfont.eot")
				+ "?#iefix') format('embedded-opentype'), "
				+ "url('"
				+ FontUtil.getFontFileURL("opensymbol-webfont.woff")
				+ "') format('woff'), "
				+ "url('"
				+ FontUtil.getFontFileURL("opensymbol-webfont.ttf")
				+ "') format('truetype'), "
				+ "url('"
				+ FontUtil.getFontFileURL("opensymbol-webfont.svg")
				+ "#OpenSymbolRegular') format('svg'); "
				+ "font-weight: normal; font-style: normal;}";
		// String eot = "url(\""
		// + FontUtil.getFontFileURL("openSymbol.ttf").toString()
		// .replace("ttf", "eot?") + "\") format(\"eot\")";
		// String woff = "url(\""
		// + FontUtil.getFontFileURL("openSymbol.ttf").toString()
		// .replace("ttf", "woff") + "\") format(\"woff\")";
		// String ttf = "url(\"" + FontUtil.getFontFileURL("OpenSymbol.ttf")
		// + "\") format(\"truetype\")";
		// return "@font-face { font-family: \"OpenSymbol\"; src: " +
		// /*
		// * eot + " " + woff + " " +
		// */
		// ttf + "}";
	}
}
