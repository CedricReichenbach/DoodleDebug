package ch.unibe.scg.doodle.rendering;

import ch.unibe.scg.doodle.htmlgen.Tag;

public class BooleanRendering implements Rendering<Boolean> {

	@Override
	public void render(Boolean bool, Tag tag) {
		Tag value = new Tag("div", "class=boolean");
		if (bool == true) {
			value.add("true");
			value.addCSSClass("true");
		} else {
			value.add("false");
			value.addCSSClass("false");
		}
		tag.add(value);
	}

	@Override
	public void renderSmall(Boolean bool, Tag tag) {
		Tag value = new Tag("div", "class=boolean");
		if (bool == true) {
			value.add("&#x2611;");
			value.addCSSClass("true");
		} else {
			value.add("&#x2610;");
			value.addCSSClass("false");
		}
		tag.add(value);
	}

}
