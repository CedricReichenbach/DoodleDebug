package rendering;

import java.lang.reflect.Array;

import html_generator.Tag;


public class ArrayRendering implements Rendering<Object[]> {

	@Override
	public void render(Object[] array, Tag tag) {
		Tag p = new Tag("p");
		p.add("here comes array visualisation");
		tag.add(p);
	}

}
