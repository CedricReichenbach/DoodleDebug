package rendering;

import java.lang.reflect.Array;

import doodle.Scratch;

import html_generator.Tag;


public class ArrayRendering implements Rendering<Object[]> {

	@Override
	public void render(Object[] array, Tag tag) {
		Tag table = new Tag("table", "border=\"1\"");
		Tag tr = new Tag("tr");
		for (int i = 0; i < array.length; i++) {
			Tag td = new Tag("td");
			
			// render inner objects
			new Scratch(array[i]).drawWhole(td);
			
			tr.add(td);
		}
		table.add(tr);
		tag.add(table);
	}

}
