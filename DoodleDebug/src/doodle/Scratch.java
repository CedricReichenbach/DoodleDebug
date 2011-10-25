package doodle;

import java.util.ArrayList;
import java.util.List;

/**
 * Organized in a tree structure, draws object and its nested objects inside.
 * 
 * @author Cedric Reichenbach
 * 
 */
public class Scratch {

	/**
	 * Scratch containing this one (for root: null)
	 */
	private Scratch top;

	/**
	 * List of Scratches contained inside this one
	 */
	private List<Scratch> inner;
	
	/**
	 * List of Scratches contained on the outside of this one
	 */
	private List<Scratch> outer;

	/**
	 * Creates a new Scratch for visualizing objects
	 */
	public Scratch() {
		this.top = null;
		this.inner = new ArrayList<Scratch>();
		this.outer = new ArrayList<Scratch>();
		this.initalize();
	}

	/**
	 * Creates a new Scratch for visualizing objects inside "top" scratch
	 * 
	 * @param Scratch
	 *            top
	 */
	public Scratch(Scratch top) {
		this.top = top;
		this.inner = new ArrayList<Scratch>();
		this.outer = new ArrayList<Scratch>();
		this.initalize();
	}

	private void initalize() {
		// TODO: Initialize rendering and displaying
	}

	/**
	 * Visualizes any Object on this whole Scratch (not inside other object).
	 * Should only be called the first time (top object).
	 * 
	 * @param o
	 */
	public void drawWhole(Object o) {
		if (o instanceof Drawable) {
			((Drawable) o).drawOn(this);
		} else {
			this.drawDefault(o, this);
		}
	}

	/**
	 * Visualizes any Object inside the caller, either using draw Method of the
	 * object itself (if existing) or does a default drawing. Should only be
	 * called from another draw Method.
	 * 
	 * @param Object
	 *            o
	 */
	public void draw(Object o) {
		Scratch subScratch = new Scratch(this);
		this.inner.add(subScratch);
		if (o instanceof Drawable) {
			((Drawable) o).drawOn(subScratch);
		} else {
			this.drawDefault(o, subScratch);
		}
	}

	/**
	 * Makes a default drawing for objects that don't implement their own draw
	 * method.
	 * 
	 * @param Object
	 *            o
	 */
	private void drawDefault(Object o, Scratch s) {
		// TODO: draw default rendering of o on s
	}

	/**
	 * Visualizes any Object with few details, either using drawSmall Method of
	 * the object itself (if existing) or does a default drawing.
	 * 
	 * @param Object
	 *            o
	 */
	public void drawSmall(Object o) {
		// TODO: Use o's drawSmall method, if existing or else, use
		// drawSmallDefault
	}

	/**
	 * Makes a default drawing with few details for objects that don't implement
	 * their own drawSmall method.
	 * 
	 * @param Object
	 *            o
	 */
	private void drawSmallDefault(Object o, Scratch s) {
		// TODO: draw default rendering of o on s
	}

}
