package rendering;

import java.awt.Color;

import html_generator.Attribute;
import html_generator.Tag;

public class ColorRendering implements Rendering<Color> {

	@SuppressWarnings("unchecked")
	@Override
	public void render(Color color, Tag tag) {
		tag.getAttributes().add(new Attribute("style", "background-color:"+hexColorString(color)));
//		tag.add(color.toString());
	}

	private String hexColorString(Color color) {
		int red = color.getRed();
		int green = color.getGreen();
		int blue = color.getBlue();
		String hex_red = size2(Integer.toHexString(red));
		String hex_green = size2(Integer.toHexString(green));
		String hex_blue = size2(Integer.toHexString(blue));
		return "#" + hex_red + hex_green + hex_blue;
	}

	private String size2(String hexString) {
		return hexString.length() < 2 ? "0" + hexString : hexString;
	}

}
