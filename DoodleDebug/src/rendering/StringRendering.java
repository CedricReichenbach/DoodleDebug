package rendering;

import html_generator.Tag;

public class StringRendering implements Rendering<String> {

	private final int maxLength = 50;

	@Override
	public void render(String string, Tag tag) {
		if (string.length() > maxLength) {
			string = string.substring(0, maxLength - 1) + "...";
		}
		tag.add(string);
	}

}
