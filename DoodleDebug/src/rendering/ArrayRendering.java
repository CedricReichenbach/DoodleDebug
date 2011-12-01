package rendering;

import java.lang.reflect.Array;

import javax.inject.Inject;

import doodle.Scratch;
import doodle.ScratchFactory;

import html_generator.Tag;


public class ArrayRendering implements Rendering<Object[]> {

	@Inject
	ScratchFactory scratchFactory;
	
	@Override
	public void render(Object[] array, Tag tag) {
		Tag div = new Tag("div", "class=arrayRendering");
		for (int i = 0; i < array.length; i++) {
			Tag span = new Tag("span");
			
			// render inner objects
			scratchFactory.create(array[i]).drawWhole(span);
			
			div.add(span);
		}
		
		tag.add(div);
	}

}
