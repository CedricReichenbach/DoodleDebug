package rendering;

import java.lang.reflect.Array;

import javax.inject.Inject;

import doodle.Doodler;
import doodle.RealScratch;
import doodle.ScratchFactory;

import html_generator.Tag;


public class ArrayRendering implements Rendering<Object[]> {

	@Inject
	Doodler doodler;
	
	@Override
	public void render(Object[] array, Tag tag) {
		Tag div = new Tag("div");
		for (int i = 0; i < array.length; i++) {
			doodler.renderInlineInto(array[i], div);
		}
		
		tag.add(div);
	}

}
