package rendering;

import java.lang.reflect.Array;

import javax.inject.Inject;

import doodle.Doodler;
import doodle.RealScratch;
import doodle.Scratch;
import doodle.ScratchFactory;

import html_generator.Attribute;
import html_generator.Tag;

public class ArrayRendering implements Rendering<Object[]> {

	@Inject
	Doodler doodler;

	@Override
	public void render(Object[] array, Tag tag) {
		Tag div = new Tag("div");

		if (checkIfElementsSameType(array)) {
			for (int i = 0; i < array.length; i++) {
				doodler.renderInlineIntoWithoutClassName(array[i], div);
			}
		} else {
			for (int i = 0; i < array.length; i++) {
				doodler.renderInlineInto(array[i], div);
			}
		}

		tag.add(div);
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
