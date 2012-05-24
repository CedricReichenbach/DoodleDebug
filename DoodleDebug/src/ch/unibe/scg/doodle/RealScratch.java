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
import ch.unibe.scg.doodle.api.DoodleCanvas;
import ch.unibe.scg.doodle.api.Doodleable;
import ch.unibe.scg.doodle.api.RealDoodleCanvas;
import ch.unibe.scg.doodle.helperClasses.ErrorDrawer;
import ch.unibe.scg.doodle.helperClasses.HtmlImage;
import ch.unibe.scg.doodle.plugins.ArrayPlugin;
import ch.unibe.scg.doodle.plugins.RenderingPlugin;
import ch.unibe.scg.doodle.rendering.Rendering;
import ch.unibe.scg.doodle.rendering.RenderingRegistry;
import ch.unibe.scg.doodle.rendering.DoodleCanvasRendering;
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

	@Inject
	RenderingRegistry renderingRegistry;

	@Inject
	Provider<ArrayPlugin> arrayPluginProvider;

	@Inject
	ScratchFactory scratchFactory;

	@Inject
	Provider<DoodleCanvasRendering> doodleCanvasRenderingProvider;

	@Inject
	Doodler doodler;

	private DoodleCanvas canvas;

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
				((Doodleable) object).drawOn(this.canvas);
			} catch (Exception e) {
				ErrorDrawer errorDrawer = new ErrorDrawer(e);
				try {
					errorDrawer.drawOn(this.canvas);
				} catch (Exception e1) {
					throw new RuntimeException(e1);
				}
			}
			Rendering<DoodleCanvas> rendering = doodleCanvasRenderingProvider.get();
			rendering.render(this.canvas, tag);
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
