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
		for (Object o : list) {
			doodler.renderInlineInto(o, div);
		}
		
		tag.add(div);
	}

}
