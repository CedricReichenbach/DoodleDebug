package doodle;

import html_generator.Tag;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import plugins.ArrayPlugin;
import plugins.RenderingPlugin;

import com.google.inject.Guice;
import com.google.inject.Injector;
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
	public void drawWhole(Tag tag) {
		if (object instanceof Drawable) {
			((Drawable) object).drawOn(this);
			Rendering<Scratch> rendering = new ScratchRendering(); //XXX
			rendering.render(this, tag);
			// TODO: Ask registry for Scratch visualization
		} else {
			this.drawDefault(tag);
		}
	}

	/**
	 * Makes a default drawing for objects that don't implement their own draw
	 * method.
	 * 
	 * @param Object
	 *            o
	 */
	private void drawDefault(Tag tag) {
		Injector injector = Guice.createInjector(new DoodleModule());
		renderingRegistry = injector
				.getInstance(RenderingRegistry.class);
		// TODO: above code should not be necessary... -.-
		
		assert(renderingRegistry != null);
		RenderingPlugin plugin = renderingRegistry.lookup(object.getClass());
		
		// XXX special case of arrays, should be managed better
		if (object.getClass().isArray()) {
			plugin = new ArrayPlugin();
		}
		
		plugin.render(object, tag);
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
//		Scratch s = scratchProvider.get(); TODO: what is this for?
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
