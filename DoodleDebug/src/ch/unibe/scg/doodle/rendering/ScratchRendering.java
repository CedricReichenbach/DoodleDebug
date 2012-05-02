package ch.unibe.scg.doodle.rendering;


import java.util.List;

import javax.inject.Inject;

import ch.unibe.ch.scg.htmlgen.Tag;
import ch.unibe.scg.doodle.Doodler;
import ch.unibe.scg.doodle.Scratch;


public class ScratchRendering implements Rendering<Scratch> {
	
	@Inject
	Doodler doodler;

	@Override
	public void render(Scratch scratch, Tag tag) {
		List<List<List<Object>>> columns = scratch.getColumns();
		
		for (List<List<Object>> column : columns) {
			Tag div = new Tag("div", "class=column");
			this.renderColumn(column, div);
			tag.add(div);
		}
	}

	private void renderColumn(List<List<Object>> column, Tag tag) {
		for (List<Object> line : column) {
			Tag div = new Tag("div");
			this.renderLine(line, div);
			tag.add(div);
			tag.add(Tag.br());
		}
	}

	private void renderLine(List<Object> line, Tag tag) {
		for (Object o : line) {
			Tag span = new Tag("span");
			doodler.renderInlineIntoWithoutClassName(o, tag);
			tag.add(span);
		}
	}
}
