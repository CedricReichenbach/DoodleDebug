package ch.unibe.scg.doodle.rendering;

import java.awt.Color;

import ch.unibe.scg.doodle.htmlgen.Attribute;
import ch.unibe.scg.doodle.htmlgen.Tag;

public class ColorRendering implements Rendering<Color> {

	@SuppressWarnings("unchecked")
	@Override
	public void render(Color color, Tag tag) {
		tag.addAttribute("style", "background-color:" + rgbaColorString(color));
		if (isDark(color))
			tag.addCSSClass("darkColor");
		Tag colorText = new Tag("div", "class=colorText");
		colorText.add(colorString(color));
		tag.add(colorText);
	}

	@Override
	public void renderSmall(Color color, Tag tag) {
		tag.getAttributes().add(
				new Attribute("style", "background-color:"
						+ rgbaColorString(color)));
	}

	private boolean isDark(Color color) {
		int r = color.getRed();
		int g = color.getGreen();
		int b = color.getBlue();
		int a = 255 - color.getAlpha();
		return (r + g + b) < 255 && a < 100; // ~ < 25% brightness
	}

	private String colorString(Color color) {
		int r = color.getRed();
		int g = color.getGreen();
		int b = color.getBlue();
		int a = color.getAlpha();
		return "RGB&alpha;: (" + r + ", " + g + ", " + b + ", " + a + ")";
	}

	private String rgbaColorString(Color color) {
		int red = color.getRed();
		int green = color.getGreen();
		int blue = color.getBlue();
		int alpha = color.getAlpha();
		return "rgba(" + red + "," + green + "," + blue + ","
				+ (((double) alpha) / 255) + ")";
	}

	private String size2(String hexString) {
		return hexString.length() < 2 ? "0" + hexString : hexString;
	}

}
