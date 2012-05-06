package ch.unibe.scg.doodle.rendering;

import javax.inject.Inject;

import ch.unibe.ch.scg.htmlgen.Tag;
import ch.unibe.scg.doodle.Doodler;

public class ArrayRendering implements Rendering<Object[]> {

	@Inject
	Doodler doodler;

	@Override
	public void render(Object[] array, Tag tag) {
		if (checkIfElementsSameType(array)) {
			for (int i = 0; i < array.length; i++) {
				Tag element = new Tag("div");
				Doodler.addClass(element, "arrayElement");
				doodler.renderInlineIntoWithoutClassName(array[i], element);
				tag.add(element);
			}
		} else {
			for (int i = 0; i < array.length; i++) {
				Tag element = new Tag("div");
				Doodler.addClass(element, "arrayElement");
				doodler.renderInlineInto(array[i], element);
				tag.add(element);
			}
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
