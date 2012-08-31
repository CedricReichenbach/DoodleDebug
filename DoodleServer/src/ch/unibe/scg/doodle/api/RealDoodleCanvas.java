package ch.unibe.scg.doodle.api;

import java.util.ArrayList;
import java.util.List;

import ch.unibe.scg.doodle.helperClasses.SmallWrapper;

public class RealDoodleCanvas implements DoodleCanvas {

	/**
	 * List of columns, a column is a list of lines, a line is a list of
	 * sketches.
	 */
	private List<List<List<Object>>> columns;

	/**
	 * Creates a new DoodleCanvas object.<br>
	 * <b>Not intended to be used by clients.</b>
	 */
	public RealDoodleCanvas() {
		List<Object> line = new ArrayList<Object>();
		List<List<Object>> column = new ArrayList<List<Object>>();
		column.add(line);
		this.columns = new ArrayList<List<List<Object>>>();
		columns.add(column);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see doodle.Scratch#draw(java.lang.Object)
	 */
	@Override
	public void draw(Object o) {
		List<List<Object>> lastColumn = columns.get(columns.size() - 1);
		List<Object> lastLine = lastColumn.get(lastColumn.size() - 1);
		lastLine.add(o);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see doodle.Scratch#drawSmall(java.lang.Object)
	 */
	@Override
	public void drawSmall(Object o) {
		draw(new SmallWrapper(o));
	}

	@Override
	public void newLine() {
		List<List<Object>> lastColumn = columns.get(columns.size() - 1);
		List<Object> line = new ArrayList<Object>();
		lastColumn.add(line);
	}

	@Override
	public void newColumn() {
		List<Object> line = new ArrayList<Object>();
		List<List<Object>> column = new ArrayList<List<Object>>();
		column.add(line);
		columns.add(column);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see doodle.Scratch#getInner()
	 */
	public List<List<List<Object>>> getColumns() {
		return this.columns;
	}

	private String getSuffix(String mimeType) {
		if (mimeType.equals("image/png")) {
			return ".png";
		}
		throw new RuntimeException();
	}
}
