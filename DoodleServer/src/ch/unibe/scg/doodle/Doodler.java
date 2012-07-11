package ch.unibe.scg.doodle;

import java.awt.Desktop;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import org.eclipse.swt.widgets.Display;

import ch.unibe.scg.doodle.helperClasses.NullObject;
import ch.unibe.scg.doodle.htmlgen.Attribute;
import ch.unibe.scg.doodle.htmlgen.Attributes;
import ch.unibe.scg.doodle.htmlgen.Tag;
import ch.unibe.scg.doodle.server.DoodleServer;
import ch.unibe.scg.doodle.server.LightboxStack;
import ch.unibe.scg.doodle.server.util.DoodleImages;
import ch.unibe.scg.doodle.server.views.HtmlShow;
import ch.unibe.scg.doodle.util.FileUtil;
import ch.unibe.scg.doodle.view.HtmlDocument;

/**
 * Class used for visualizing any Object
 * 
 * @author Cedric Reichenbach
 * 
 */
public class Doodler {

	@Inject
	ScratchFactory scratchFactory;
	@Inject
	SmallScratchFactory smallScratchFactory;

	private Tag body;

	private final boolean debugMode = true; // XXX

	private int level;

	private IndexedObjectStorage clickables;

	private static Doodler instance; // XXX Sorry, Guice...

	/**
	 * Creates a new Doodler for visualizing objects 1 Doodler = 1 window
	 */
	protected Doodler() {
		body = new Tag("body");
		setBackgroundImage();
		prepareLightbox();
		clickables = new IndexedObjectStorage();
		instance = this;
	}

	public static Doodler instance() {
		return instance;
	}

	private void setBackgroundImage() {
		String texPath = DoodleImages.getDoodleTextureImageFilePath();
		body.addAttribute("style", "background-image:url(" + texPath + ")");
	}

	private void prepareLightbox() {
		Tag lighboxWrapper = new Tag("div", "id=lightboxWrapper");
		lighboxWrapper.addAttribute("style", "visibility:hidden");
		Tag overlay = new Tag("div", "id=overlay");
		overlay.addAttribute("onclick", "javascript:hideLightbox()");
		lighboxWrapper.add(overlay);
		Tag lightbox = new Tag("div", "id=lightbox");
		lighboxWrapper.add(lightbox);
		String closeImgFilePath = DoodleImages.getCloseWindowImageFilePath();
		Tag closeImg = new Tag("img", "id=closeButton");
		closeImg.addAttribute("src", closeImgFilePath);
		closeImg.addAttribute("onclick", "javascript:hideLightbox()");
		lighboxWrapper.add(closeImg);
		body.add(lighboxWrapper);
	}

	/**
	 * Visualizes any Object, either using draw Method of the object itself (if
	 * existing) or does a default drawing.
	 * 
	 * @param Object
	 * 
	 */
	@SuppressWarnings("unchecked")
	public void visualize(Object o) {
		if (o == null) {
			o = new NullObject();
		}

		Scratch scratch = scratchFactory.create(o);
		scratch.addCSSClass("printOut");
		this.level = 0;
		body.addAttribute(new Attribute("class", "rendering"));
		scratch.drawWholeWithName(body);

		body.add(new Tag("hr", "class=betweenDrawCalls"));
		HtmlDocument htmlDocument = new HtmlDocument();
		htmlDocument.setBody(body);

		DoodleServer.instance().setStorage(clickables);
		Runnable htmlShow = new HtmlShow(htmlDocument.toString());
		Display.getDefault().syncExec(htmlShow);
	}

	public void renderInlineInto(Object object, Tag tag) {
		renderInline(object, tag, true);
	}

	public void renderInlineIntoWithoutClassName(Object object, Tag tag) {
		renderInline(object, tag, false);
	}

	public void renderIntoLightbox(LightboxStack stack, Tag tag) {
		level--;
		renderBreadcrumbs(stack, tag);
		renderInline(stack.top(), tag, false);
		level++;
	}

	private void renderBreadcrumbs(LightboxStack stack, Tag tag) {
		Tag breadcrumbs = new Tag("div", "id=breadcrumbs");
		List<Object> objects = stack.bottomUpList();
		for (Object o : objects) {
			breadcrumbs.add(">");
			String name = scratchFactory.create(o).getObjectTypeName();
			breadcrumbs.add(name);
		}
		tag.add(breadcrumbs);
	}

	private void renderInline(Object object, Tag tag, boolean withClassName) {
		if (object == null) {
			object = new NullObject();
		}
		level++; // TODO: smarter, nicer solution for this
		addClass(tag, "rendering");
		Scratch scratch = null;
		if (level <= 1) {
			scratch = scratchFactory.create(object);
		} else {
			scratch = smallScratchFactory.create(object);
		}
		scratch.addCSSClass("level" + level);
		scratch.setLevel(level);
		if (level == 1)
			scratch.setObjectID(clickables.store(object));
		if (withClassName) {
			scratch.drawWholeWithName(tag);
		} else {
			scratch.drawWhole(tag);
		}
		level--;
	}

	public static void addClass(Tag tag, String className) {
		Attributes attributes = tag.getAttributes(); // XXX
		for (Attribute a : attributes) {
			String attName = a.getAttribute();
			if (attName.equals("class")) {
				List<String> vals = Arrays.asList(a.getValue().split(" "));
				if (vals.contains(className))
					return;
				a.setValue(a.getValue() + " " + className);
				tag.setAttributes(attributes);
				return;
			}
		}
		tag.addAttribute(new Attribute("class", className));
	}
}