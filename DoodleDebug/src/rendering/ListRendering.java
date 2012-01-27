package rendering;

import html_generator.Tag;

import java.util.List;

import javax.inject.Inject;

import doodle.Doodler;

public class ListRendering implements Rendering<List> {

	@Inject
	Doodler doodler;

	@Override
	public void render(List list, Tag tag) {
		Tag div = new Tag("div");

		if (checkIfElementsSameType(list)) {
			for (Object o : list) {
				doodler.renderInlineIntoWithoutClassName(o, div);
			}
		} else {
			for (Object o : list) {
				doodler.renderInlineInto(o, div);
			}
		}
		tag.add(div);
	}

	public static boolean checkIfElementsSameType(List list) {
		if (list.isEmpty())
			return true;

		Class first = list.get(0).getClass();

		for (Object o : list) {
			if (!(o.getClass().equals(first)))
				return false;
		}
		return true;
	}

}
