package ch.unibe.scg.doodle.rendering;

import java.util.Collection;

import javax.inject.Inject;

import ch.unibe.scg.doodle.Doodler;
import ch.unibe.scg.doodle.htmlgen.Tag;

public class CollectionRendering implements Rendering<Collection<?>> {

	@Inject
	Doodler doodler;

	@Override
	public void render(Collection<?> collection, Tag tag) {
		if (checkIfElementsSameType(collection)) {
			for (Object o : collection) {
				Tag element = new Tag("div");
				element.addCSSClass("collectionElement");
				doodler.renderInlineIntoWithoutClassName(o, element);
				tag.add(element);
			}
		} else {
			for (Object o : collection) {
				Tag element = new Tag("div");
				element.addCSSClass("collectionElement");
				doodler.renderInlineInto(o, element);
				tag.add(element);
			}
		}
	}

	@Override
	public void renderSmall(Collection<?> collection, Tag tag) {
		int threshold = 10;
		int painted = 0;
		for (Object o : collection) {
			if (painted >= threshold) {
				Tag dot = new Tag("div", "class=collectionDot");
				dot.addCSSClass("collectionElement");
				for (int i = 0; i < 3; i++) {
					tag.add(dot);
				}
				break;
			}
			Tag element = new Tag("div", "class=collectionElement");
			tag.add(element);
			painted++;
		}
	}

	public static boolean checkIfElementsSameType(Collection<?> collection) {
		if (collection.size() == 0)
			return true;

		try {
			Class<?> first = collection.iterator().next().getClass();

			for (Object o : collection) {
				if (!(o.getClass().equals(first)))
					return false;
			}
		} catch (NullPointerException e) {
			return false;
		}
		return true;
	}

}
