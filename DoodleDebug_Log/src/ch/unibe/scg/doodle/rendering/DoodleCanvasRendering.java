package ch.unibe.scg.doodle.rendering;

import java.util.List;

import javax.inject.Inject;

import ch.unibe.scg.doodle.Doodler;
import ch.unibe.scg.doodle.api.RealDoodleCanvas;
import ch.unibe.scg.doodle.helperClasses.SmallWrapper;
import ch.unibe.scg.doodle.htmlgen.Tag;

public class DoodleCanvasRendering implements Rendering<RealDoodleCanvas> {

	@Inject
	Doodler doodler;

	@SuppressWarnings("unchecked")
	@Override
	public void render(RealDoodleCanvas canvas, Tag tag) {
		List<List<List<Object>>> columns = canvas.getColumns();

		for (List<List<Object>> column : columns) {
			Tag div = new Tag("div", "class=column");
			this.renderColumn(column, div);
			tag.add(div);
		}
	}

	@Override
	public void renderSimplified(RealDoodleCanvas canvas, Tag tag) {
		this.render(canvas, tag); // Differentiation is done in Interface
									// Doodleable
	}

	@SuppressWarnings("unchecked")
	private void renderColumn(List<List<Object>> column, Tag tag) {
		for (List<Object> line : column) {
			Tag div = new Tag("div", "class=line");
			this.renderLine(line, div);
			tag.add(div);
		}
	}

	@SuppressWarnings("unchecked")
	private void renderLine(List<Object> line, Tag tag) {
		for (Object o : line) {
			Tag div = new Tag("div");
			if (o instanceof SmallWrapper)
				doodler.renderSmallInlineInto(((SmallWrapper) o).object(), div);
			else
				doodler.renderInlineIntoWithoutClassName(o, div);
			tag.add(div);
		}
	}
}
