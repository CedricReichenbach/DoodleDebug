package ch.unibe.scg.doodle;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

import ch.unibe.ch.scg.htmlgen.Attribute;
import ch.unibe.ch.scg.htmlgen.Tag;
import ch.unibe.scg.doodle.helperClasses.ErrorDrawer;
import ch.unibe.scg.doodle.helperClasses.HtmlImage;
import ch.unibe.scg.doodle.plugins.ArrayPlugin;
import ch.unibe.scg.doodle.plugins.RenderingPlugin;
import ch.unibe.scg.doodle.rendering.Rendering;
import ch.unibe.scg.doodle.rendering.RenderingRegistry;
import ch.unibe.scg.doodle.rendering.ScratchRendering;
import ch.unibe.scg.doodle.view.CSSCollection;

import com.google.inject.Provider;
import com.google.inject.assistedinject.Assisted;

/**
 * Placeholder for an object. Finds the correct plugin to display an object and
 * prepares the tag the object can write itself into.
 * 
 * Organized in a tree structure, which the renderer traverses to create the
 * real pixels on a doodle canvas.
 * 
 * @author Cedric Reichenbach
 * 
 */
public class RealScratch implements Scratch {

	private Object object;

	private String title;

	/**
	 * List of columns, a column is a list of lines, a line is a list of
	 * sketches.
	 */
	private List<List<List<Object>>> columns;

	@Inject
	RenderingRegistry renderingRegistry;

	@Inject
	Provider<ArrayPlugin> arrayPluginProvider;

	@Inject
	ScratchFactory scratchFactory;

	@Inject
	Provider<ScratchRendering> scratchRenderingProvider;

	@Inject
	Doodler doodler;

	private int level;

	private String classNames = "";

	private int objectID;

	/**
	 * Creates a new Scratch for visualizing objects
	 */
	@Inject
	RealScratch(@Assisted Object o) {
		this.object = o;
		this.title = "";

		List<Object> line = new ArrayList<Object>();
		List<List<Object>> column = new ArrayList<List<Object>>();
		column.add(line);
		this.columns = new ArrayList<List<List<Object>>>();
		columns.add(column);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see doodle.Scratch#drawWhole(html_generator.Tag)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void drawWhole(Tag tag) {
		Tag subTag = new Tag("div");
		subTag.addAttribute(new Attribute("class", classNames));
		tag.add(subTag);

		drawRendering(subTag);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void drawWholeWithName(Tag tag) {
		writeClassName(renderingRegistry.lookup(object.getClass())
				.getObjectTypeName(object), tag);
		Tag subTag = new Tag("div");
		subTag.addAttribute(new Attribute("class", classNames));
		tag.add(subTag);
		this.drawRendering(subTag);
	}

	void drawRendering(Tag tag) {
		addLink(tag);

		if (object instanceof Doodleable) {
			tag.addAttribute(new Attribute("class", "Scratch"));
			try {
				((Doodleable) object).drawOn(this);
			} catch (Exception e) {
				ErrorDrawer errorDrawer = new ErrorDrawer(e);
				try {
					errorDrawer.drawOn(this);
				} catch (Exception e1) {
					throw new RuntimeException(e1);
				}
			}
			Rendering<Scratch> rendering = scratchRenderingProvider.get();
			rendering.render(this, tag);
		} else {
			this.drawDefault(tag);
		}
	}

	private void writeClassName(String name, Tag tag) {
		Tag span = new Tag("div", "class=ClassName");
		span.add(name);
		tag.add(span);
	}

	private void addLink(Tag tag) {
		if (this.level == 1) {
			tag.addAttribute(new Attribute("onclick", "window.location = '"
					+ objectID + "'"));
			tag.addAttribute(new Attribute("rel", "lightbox"));
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
		assert (renderingRegistry != null);
		RenderingPlugin plugin = renderingRegistry.lookup(object.getClass());
		prepareTag(tag, plugin);

		CSSCollection.instance().add(plugin.getCSS());

		if (object.getClass().isArray()) {
			plugin = arrayPluginProvider.get();
		}

		plugin.render(object, tag);
	}

	void prepareTag(Tag tag, RenderingPlugin plugin) {
		Doodler.addClass(tag, plugin.getClassAttribute());
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
		// TODO: SmallScratch for this case
		RealScratch subScratch = new SmallScratch(o);
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
	public List<List<List<Object>>> getColumns() {
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
		String classAttribute = renderingRegistry.lookup(object.getClass())
				.getClassAttribute();
		return classAttribute;
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

	private String getSuffix(String mimeType) {
		if (mimeType.equals("image/png")) {
			return ".png";
		}
		throw new RuntimeException();
	}

	@Override
	public void addCSSClass(String className) {
		classNames += classNames.isEmpty() ? className : " " + className;
	}

	@Override
	public void setLevel(int level) {
		this.level = level;
	}

	@Override
	public void setObjectID(int id) {
		this.objectID = id;
	}

}
