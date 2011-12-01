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
import com.google.inject.assistedinject.Assisted;

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
public class Scratch implements ScratchInterface {

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
	Provider<ArrayPlugin> arrayPluginProvider;
	


	/**
	 * Creates a new Scratch for visualizing objects
	 */
	@Inject
	Scratch(@Assisted Object o) {
		this.inner = new ArrayList<Scratch>();
		this.object = o;
		this.outer = new ArrayList<Scratch>();
		this.title = "";
	}
	

	/* (non-Javadoc)
	 * @see doodle.Scratch#drawWhole(html_generator.Tag)
	 */
	@Override
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
		assert(renderingRegistry != null);
		RenderingPlugin plugin = renderingRegistry.lookup(object.getClass());
		
		if (object.getClass().isArray()) {
			plugin = arrayPluginProvider.get();
		}
		
		plugin.render(object, tag);
	}

	/* (non-Javadoc)
	 * @see doodle.Scratch#draw(java.lang.Object)
	 */
	@Override
	public void draw(Object o) {
//		Scratch s = scratchProvider.get(); TODO: what is this for?
		Scratch subScratch = new Scratch(o);
		this.inner.add(subScratch);
	}

	/* (non-Javadoc)
	 * @see doodle.Scratch#drawOuter(java.lang.Object)
	 */
	@Override
	public void drawOuter(Object o) {
		Scratch subScratch = new Scratch(o);
		this.outer.add(subScratch);
	}

	/* (non-Javadoc)
	 * @see doodle.Scratch#drawSmall(java.lang.Object)
	 */
	@Override
	public void drawSmall(Object o) {
		// TODO: SmallScratch for this case
		Scratch subScratch = new SmallScratch(o);
		this.inner.add(subScratch);
	}

	/* (non-Javadoc)
	 * @see doodle.Scratch#drawTitle(java.lang.String)
	 */
	@Override
	public void drawTitle(String title) {
		this.title = title;
	}

	/* (non-Javadoc)
	 * @see doodle.Scratch#getInner()
	 */
	@Override
	public List<Scratch> getInner() {
		return this.inner;
	}

	/* (non-Javadoc)
	 * @see doodle.Scratch#getOuter()
	 */
	@Override
	public List<Scratch> getOuter() {
		return outer;
	}

	/* (non-Javadoc)
	 * @see doodle.Scratch#getTitle()
	 */
	@Override
	public String getTitle() {
		return title;
	}

}
