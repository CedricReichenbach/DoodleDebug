package view;

import java.awt.geom.Area;
import java.util.ArrayList;
import java.util.List;

import doodle.Scratch;

/**
 * This Class represents a Scratch and holds information about its position in
 * parent ScratchCell and contains nested ScratchCells, inner and outer ones.
 * It's organized similar to Scratch.
 * 
 * @author Cedric Reichenbach
 * 
 */
public class ScratchCell {

	private ScratchCell top;
	private List<ScratchCell> inner;
	private List<ScratchCell> outer;
	/** Defines area in top ScratchCell (relatively, both axes in [0,1]) */
	private ScratchArea areaInTop;

	/**
	 * Creates a ScratchCell representing scratch (for drawing)
	 * 
	 * @param Scratch
	 *            scratch
	 */
	public ScratchCell(Scratch scratch) {
		try {
			this.top = new ScratchCell(scratch.getTop());
		} catch (NullPointerException npe) {
			this.top = null;
		}
		this.inner = new ArrayList<ScratchCell>();
		for (Scratch in : scratch.getInner()) {
			this.inner.add(new ScratchCell(in));
		}
		this.outer = new ArrayList<ScratchCell>();
		for (Scratch out : scratch.getOuter()) {
			this.outer.add(new ScratchCell(out));
		}
		
		// areas
		if (this.top == null) {
			this.areaInTop = new ScratchArea(0, 0, 1, 1);
		}
		this.defineSubAreas();
	}

	private void defineSubAreas() {
		// TODO: define appropriate areas for children
	}

}
