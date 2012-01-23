package doodle;

import html_generator.Attribute;
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
 * The graphical representation of an object. Draws its object and its object's
 * nested objects.
 * 
 * Organized in a tree structure, which the renderer traverses to create the
 * real pixels on a doodle canvas.
 * 
 * @author Cedric Reichenbach
 * 
 */
public class RealScratch implements Scratch {

	private Object object;

	Drawable errorObject; // XXX

	private String title;

	/**
	 * List of columns, a column is a list of lines, a line is a list of
	 * sketches.
	 */
	private List<List<List<Scratch>>> columns;

	@Inject
	RenderingRegistry renderingRegistry;

	@Inject
	Provider<ArrayPlugin> arrayPluginProvider;

	@Inject
	ScratchFactory scratchFactory;
	
	@Inject
	Provider<ScratchRendering> scratchRenderingProvider;

	/**
	 * Creates a new Scratch for visualizing objects
	 */
	@Inject
	RealScratch(@Assisted Object o) {
		this.object = o;
		this.title = "";

		List<Scratch> line = new ArrayList<Scratch>();
		List<List<Scratch>> column = new ArrayList<List<Scratch>>();
		column.add(line);
		this.columns = new ArrayList<List<List<Scratch>>>();
		columns.add(column);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see doodle.Scratch#drawWhole(html_generator.Tag)
	 */
	@Override
	public void drawWhole(Tag tag) {
		if (object instanceof Drawable) {
			try {
				((Drawable) object).drawOn(this);
			} catch (Exception e) {
				// XXX
				try {
					errorObject.drawOn(this);
				} catch (Exception e1) {
					throw new RuntimeException();
				}
			}
			Rendering<Scratch> rendering = scratchRenderingProvider.get();
			rendering.render(this, tag);
		} else {
			this.drawDefault(tag);
		}

//		this.setClassAttributes(tag, object); // XXX
	}

	/**
	 * Makes a default drawing for objects that don't implement their own draw
	 * method.
	 * 
	 * @param Object
	 *            o
	 */
	private void drawDefault(Tag tag) {
		assert (renderingRegistry != null);
		RenderingPlugin plugin = renderingRegistry.lookup(object.getClass());
	
		if (object.getClass().isArray()) {
			plugin = arrayPluginProvider.get();
		}
	
		plugin.render(object, tag);
	}

	/**
	 * Brutal method for writing information about object type into class
	 * attribute of just written tags inside this one
	 * 
	 * @param tag
	 * @param object2
	 */
	private void setClassAttributes(Tag tag, Object object) {
		for (Object sub : tag) {
			if (sub instanceof Tag) {
				((Tag) sub).addAttribute(new Attribute("class", object
						.getClass().getCanonicalName()));
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see doodle.Scratch#draw(java.lang.Object)
	 */
	@Override
	public void draw(Object o) {
		List<List<Scratch>> lastColumn = columns.get(columns.size()-1);
		List<Scratch> lastLine = lastColumn.get(lastColumn.size()-1);
		lastLine.add((RealScratch) scratchFactory.create(o));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see doodle.Scratch#drawSmall(java.lang.Object)
	 */
	@Override
	public void drawSmall(Object o) {
		// TODO: SmallScratch for this case
		RealScratch subScratch = new SmallScratch(o);
		// this.inner.add(subScratch);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see doodle.Scratch#drawTitle(java.lang.String)
	 */
	@Override
	public void drawTitle(String title) {
		this.title = title;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see doodle.Scratch#getInner()
	 */
	@Override
	public List<List<List<Scratch>>> getColumns() {
		return this.columns;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see doodle.Scratch#getTitle()
	 */
	@Override
	public String getTitle() {
		return title;
	}

	@Override
	public String getClassAttribute() {

		return renderingRegistry.lookup(object.getClass()).getClassAttribute();
	}

	@Override
	public void newLine() {
		List<List<Scratch>> lastColumn = columns.get(columns.size() - 1);
		List<Scratch> line = new ArrayList<Scratch>();
		lastColumn.add(line);
	}

	@Override
	public void newColumn() {
		List<Scratch> line = new ArrayList<Scratch>();
		List<List<Scratch>> column = new ArrayList<List<Scratch>>();
		column.add(line);
		columns.add(column);
	}

}
