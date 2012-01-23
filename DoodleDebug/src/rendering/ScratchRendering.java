package rendering;

import html_generator.Attributes;
import html_generator.Tag;

import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.util.List;

import view.DoodleCanvas;
import view.Rect;
import doodle.RealScratch;
import doodle.Scratch;

public class ScratchRendering implements Rendering<Scratch> {

	@Override
	public void render(Scratch scratch, Tag tag) {
		List<List<List<Scratch>>> columns = scratch.getColumns();
		
		for (List<List<Scratch>> column : columns) {
			Tag table = new Tag("table", "style=display:inline-table");
			this.renderColumn(column, table);
			tag.add(table);
		}
	}

	private void renderColumn(List<List<Scratch>> column, Tag tag) {
		for (List<Scratch> line : column) {
			Tag tr = new Tag("tr");
			this.renderLine(line, tr);
			tag.add(tr);
		}
	}

	private void renderLine(List<Scratch> line, Tag tag) {
		for (Scratch scratch : line) {
			Tag td = new Tag("td");
			scratch.drawWhole(td);
			tag.add(td);
		}
	}
}
