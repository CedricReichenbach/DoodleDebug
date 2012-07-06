package ch.unibe.scg.doodle.rendering;

import java.util.List;

import javax.inject.Inject;

import ch.unibe.scg.doodle.Doodler;
import ch.unibe.scg.doodle.htmlgen.Tag;

public class ListRendering implements Rendering<List> {

	@Inject
	Doodler doodler;

	@Override
	public void render(List list, Tag tag) {
		if (checkIfElementsSameType(list)) {
			for (Object o : list) {
				Tag element = new Tag("div");
				Doodler.addClass(element, "listElement");
				doodler.renderInlineIntoWithoutClassName(o, element);
				tag.add(element);
			}
		} else {
			for (Object o : list) {
				Tag element = new Tag("div");
				Doodler.addClass(element, "listElement");
				doodler.renderInlineInto(o, element);
				tag.add(element);
			}
		}
	}

	public static boolean checkIfElementsSameType(List list) {
		if (list.isEmpty())
			return true;

		Class first = list.get(0).getClass();

		for (Object o : list) {
			if (o == null || !(o.getClass().equals(first)))
				return false;
		}
		return true;
	}

}
