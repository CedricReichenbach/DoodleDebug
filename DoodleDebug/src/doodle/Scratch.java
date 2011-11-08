package doodle;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import com.google.inject.Provider;

import rendering.DefaultRendering;
import rendering.Rendering;
import rendering.RenderingRegistry;
import rendering.ScratchRendering;

import view.DoodleCanvas;

/**
 * The graphical representation of an object. 
 * Draws its object and its object's nested objects.
 * 
 * Organized in a tree structure, which the renderer traverses to create the real pixels on a doodle canvas.
 * 
 * @author Cedric Reichenbach
 * 
 */
public class Scratch {

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
	
	@Inject 
	RenderingRegistry renderingRegistry;
	
	@Inject
	Provider<Scratch> scratchProvider;

	/**
	 * Creates a new Scratch for visualizing objects
	 */
	public Scratch(Object o) {
		this.inner = new ArrayList<Scratch>();
		this.outer = new ArrayList<Scratch>();
		this.object = o;
		this.title = "";
	}

	/**
	 * Visualize any Object on this whole Scratch (not inside another object).
	 * 
	 * 
	 * @param canvas 
	 */
	private void drawWhole(DoodleCanvas canvas) {
		if (object instanceof Drawable) {
			((Drawable) object).drawOn(this);
			Rendering rendering = new ScratchRendering(); //XXX
			rendering.render(object, canvas);
			// TODO: Ask registry for Scratch visualization
		} else {
			this.drawDefault(canvas);
		}
	}

	/**
	 * Makes a default drawing for objects that don't implement their own draw
	 * method.
	 * 
	 * @param Object
	 *            o
	 */
	private void drawDefault(DoodleCanvas canvas) {
		// TODO: Ask Registry for visualization.
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
		Scratch s = scratchProvider.get();
		Scratch subScratch = new Scratch(o);
		this.inner.add(subScratch);
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

	public String getTitle() {
		return title;
	}

}
