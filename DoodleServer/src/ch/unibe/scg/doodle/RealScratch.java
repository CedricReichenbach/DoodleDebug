package ch.unibe.scg.doodle;

import javax.inject.Inject;

import ch.unibe.scg.doodle.api.DoodleCanvas;
import ch.unibe.scg.doodle.api.Doodleable;
import ch.unibe.scg.doodle.api.RealDoodleCanvas;
import ch.unibe.scg.doodle.helperClasses.ErrorDrawer;
import ch.unibe.scg.doodle.htmlgen.Attribute;
import ch.unibe.scg.doodle.htmlgen.Tag;
import ch.unibe.scg.doodle.plugins.ArrayPlugin;
import ch.unibe.scg.doodle.plugins.RenderingPlugin;
import ch.unibe.scg.doodle.plugins.TablePlugin;
import ch.unibe.scg.doodle.rendering.DoodleCanvasRendering;
import ch.unibe.scg.doodle.rendering.DoodleRenderException;
import ch.unibe.scg.doodle.rendering.Rendering;
import ch.unibe.scg.doodle.rendering.RenderingRegistry;
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

	protected Object object;

	@Inject
	RenderingRegistry renderingRegistry;

	@Inject
	Provider<ArrayPlugin> arrayPluginProvider;

	@Inject
	Provider<TablePlugin> tablePluginProvider;

	@Inject
	ScratchFactory scratchFactory;

	@Inject
	Provider<DoodleCanvasRendering> doodleCanvasRenderingProvider;

	@Inject
	Doodler doodler;

	protected RealDoodleCanvas canvas;

	private int level;

	private String classNames = "";

	private int objectID;

	/**
	 * Creates a new Scratch for visualizing objects
	 */
	@Inject
	RealScratch(@Assisted Object o) {
		this.object = o;
		this.canvas = new RealDoodleCanvas();
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
		writeClassName(getObjectTypeName(), tag);
		Tag subTag = new Tag("div");
		subTag.addAttribute(new Attribute("class", classNames));
		tag.add(subTag);
		this.drawRendering(subTag);
	}

	@Override
	public String getObjectTypeName() {
		return renderingRegistry.lookup(object.getClass()).getObjectTypeName(
				object);
	}

	void drawRendering(Tag tag) {
		addLink(tag);

		if (object instanceof Doodleable) {
			tag.addAttribute(new Attribute("class", "Scratch"));
			drawDoodleable();
			Rendering<RealDoodleCanvas> rendering = doodleCanvasRenderingProvider
					.get();
			try {
				rendering.render(this.canvas, tag);
			} catch (DoodleRenderException e) {
				doodler.renderInlineIntoWithoutClassName(e, tag);
			}
		} else {
			this.drawDefault(tag);
		}
	}

	protected void drawDoodleable() {
		try {
			((Doodleable) object).doodleOn(this.canvas);
		} catch (Exception e) {
			ErrorDrawer errorDrawer = new ErrorDrawer(e);
			try {
				errorDrawer.doodleOn(this.canvas);
			} catch (Exception e1) {
				throw new RuntimeException(e1);
			}
		}
	}

	private void writeClassName(String name, Tag tag) {
		Tag span = new Tag("div", "class=ClassName");
		span.add(name);
		tag.add(span);
	}

	private void addLink(Tag tag) {
		if (this.level == 1) {
			tag.addAttribute(new Attribute("onclick",
					"javascript:renderObjectInLightbox(" + objectID + ")"));
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

		try {
			renderUsingPlugin(tag, plugin);
		} catch (DoodleRenderException e) {
			doodler.renderInlineIntoWithoutClassName(e, tag);
		}
	}

	@SuppressWarnings("unchecked")
	protected void renderUsingPlugin(Tag tag, RenderingPlugin plugin)
			throws DoodleRenderException {
//		plugin.getClass().getMethod("render", Object.class, Tag.class).invoke(null, null);
		plugin.render(object, tag);
	}

	protected void prepareTag(Tag tag, RenderingPlugin plugin) {
		tag.addCSSClass(plugin.getClassAttribute());
	}

	@Override
	public String getClassAttribute() {
		String classAttribute = renderingRegistry.lookup(object.getClass())
				.getClassAttribute();
		return classAttribute;
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
