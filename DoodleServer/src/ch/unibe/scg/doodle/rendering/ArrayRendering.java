package ch.unibe.scg.doodle.rendering;

import javax.inject.Inject;

import ch.unibe.scg.doodle.Doodler;
import ch.unibe.scg.doodle.htmlgen.Tag;

public class ArrayRendering implements Rendering<Object[]> {

	@Inject
	Doodler doodler;

	@Override
	public void render(Object[] array, Tag tag) {
		if (checkIfElementsSameType(array)) {
			for (Object o : array) {
				Tag element = new Tag("div");
				element.addCSSClass("arrayElement");
				doodler.renderInlineIntoWithoutClassName(o, element);
				tag.add(element);
			}
		} else {
			for (Object o : array) {
				Tag element = new Tag("div");
				element.addCSSClass("arrayElement");
				doodler.renderInlineInto(o, element);
				tag.add(element);
			}
		}
	}

	@Override
	public void renderSmall(Object[] array, Tag tag) {
		for (Object o : array) {
			Tag element = new Tag("div", "class=arrayElement");
			tag.add(element);
		}
	}

	public static boolean checkIfElementsSameType(Object[] array) {
		if (array.length == 0)
			return true;

		try {
			Class first = array[0].getClass();

			for (Object o : array) {
				if (!(o.getClass().equals(first)))
					return false;
			}
		} catch (NullPointerException e) {
			return false;
		}
		return true;
	}

}
