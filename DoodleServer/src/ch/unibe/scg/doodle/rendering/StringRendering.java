package ch.unibe.scg.doodle.rendering;

import ch.unibe.scg.doodle.htmlgen.Tag;

public class StringRendering implements Rendering<String> {

	private final int maxLength = 50;

	@SuppressWarnings("unchecked")
	@Override
	public void render(String string, Tag tag) {
		Tag p = new Tag("p", "class=StringRendering");

		p.add(replaceRegEx(string));
		tag.add(p);
	}

	@Override
	public void renderSmall(String string, Tag tag) {
		Tag p = new Tag("p", "class=StringRendering");

		if (string.length() > maxLength) {
			string = string.substring(0, maxLength - 1) + "...";
		}

		p.add(replaceRegEx(string));
		tag.add(p);
	}

	private String replaceRegEx(String string) {
		return string.replace("\n", "<br>");
	}

}
