package ch.unibe.scg.doodle.rendering;

import ch.unibe.scg.doodle.htmlgen.Tag;

public class BooleanRendering implements Rendering<Boolean> {

	@Override
	public void render(Boolean bool, Tag tag) {
		Tag boolTag = new Tag("div", "class=boolean");
		Tag value = new Tag("div");
		if (bool == true) {
			value.add("true");
			value.addCSSClass("true");
		} else {
			value.add("false");
			value.addCSSClass("false");
		}
		boolTag.add(value);
		tag.add(boolTag);
	}

	@Override
	public void renderSmall(Boolean bool, Tag tag) {
		Tag boolTag = new Tag("div", "class=boolean");
		Tag value = new Tag("div");
		if (bool == true) {
			// value.add("&#x22A4;");
			value.addCSSClass("true");
		} else {
			// value.add("&perp;");
			value.addCSSClass("false");
		}
		boolTag.add(value);
		tag.add(boolTag);
	}

}
