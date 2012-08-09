package ch.unibe.scg.doodle.api;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ch.unibe.scg.doodle.RealScratch;
import ch.unibe.scg.doodle.SmallScratch;
import ch.unibe.scg.doodle.helperClasses.HtmlImage;

public class RealDoodleCanvas implements DoodleCanvas {

	/**
	 * List of columns, a column is a list of lines, a line is a list of
	 * sketches.
	 */
	private List<List<List<Object>>> columns;

	// TODO: make this constructor invisible for user
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
		// TODO: SmallDoodleCanvas for this case
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

	@Override
	public void drawImage(byte[] image, String mimeType) throws IOException {
		File file = File.createTempFile("image", this.getSuffix(mimeType));
		FileOutputStream out = new FileOutputStream(file);
		out.write(image);
		out.close();

		HtmlImage htmlImage = new HtmlImage(file, mimeType);
		this.draw(htmlImage);
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
