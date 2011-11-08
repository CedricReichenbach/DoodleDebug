package doodle;

import java.util.ArrayList;
import java.util.List;

import rendering.DefaultRendering;
import rendering.ScratchRendering;

import view.DoodleCanvas;

/**
 * Organized in a tree structure, draws object and its nested objects inside.
 * 
 * @author Cedric Reichenbach
 * 
 */
public class Scratch {

	private DoodleCanvas canvas;
	private Object object;

	/**
	 * List of Scratches contained inside this one
	 */
	private List<Scratch> inner;

	/**
	 * List of Scratches contained on the outside of this one
	 */
	private List<Scratch> outer;

	private String title;

	/**
	 * Creates a new Scratch for visualizing objects
	 */
	public Scratch(Object o) {
		this.inner = new ArrayList<Scratch>();
		this.outer = new ArrayList<Scratch>();
		this.object = o;
		this.title = "";
		this.drawWhole();
	}

	/**
	 * Visualizes any Object on this whole Scratch (not inside other object).
	 * Should only be called the first time (top object).
	 * 
	 * @param o
	 * @return
	 */
	private void drawWhole() {
		if (object instanceof Drawable) {
			((Drawable) object).drawOn(this);
			// TODO: Ask registry for Scratch visualization
			this.canvas = new ScratchRendering().render(this);
		} else {
			this.drawDefault();
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
		Scratch subScratch = new Scratch(o);
		this.inner.add(subScratch);
		subScratch.drawWhole();
	}

	/**
	 * Visualizes any Object outside of the caller, either using draw Method of
	 * the object itself (if existing) or does a default drawing. Should only be
	 * called from another draw Method.
	 * 
	 * @param Object
	 *            o
	 */
	public void drawOuter(Object o) {
		Scratch subScratch = new Scratch(o);
		this.outer.add(subScratch);
		subScratch.drawWhole();
	}

	/**
	 * Makes a default drawing for objects that don't implement their own draw
	 * method.
	 * 
	 * @param Object
	 *            o
	 */
	private void drawDefault() {
		// TODO: Ask Registry for visualization.
		this.canvas = new DefaultRendering().render(object);
	}

	/**
	 * Visualizes any Object with few details, either using drawSmall Method of
	 * the object itself (if existing) or does a default drawing.
	 * 
	 * @param Object
	 *            o
	 */
	public void drawSmall(Object o) {
		// TODO: SmallScratch for this case
		Scratch subScratch = new SmallScratch(o);
		this.inner.add(subScratch);
		subScratch.drawWhole();
	}

	/**
	 * Sets a title for object to be visualized
	 * 
	 * @param String
	 *            title
	 */
	public void drawTitle(String title) {
		this.title = title;
	}

	public List<Scratch> getInner() {
		return this.inner;
	}

	public List<Scratch> getOuter() {
		return outer;
	}

	public DoodleCanvas getCanvas() {
		return this.canvas;
	}

	public String getTitle() {
		return title;
	}

}
