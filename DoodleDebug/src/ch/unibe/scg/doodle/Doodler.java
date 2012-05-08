package ch.unibe.scg.doodle;

import java.awt.Desktop;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.commons.collections.map.HashedMap;

import ch.unibe.ch.scg.htmlgen.Attribute;
import ch.unibe.ch.scg.htmlgen.Attributes;
import ch.unibe.ch.scg.htmlgen.Tag;
import ch.unibe.scg.doodle.helperClasses.NullObject;
import ch.unibe.scg.doodle.simon.SimonClient;
import ch.unibe.scg.doodle.view.HtmlDocument;
import ch.unibe.scg.doodle.view.HtmlRenderer;

/**
 * Class used for visualizing any Object
 * 
 * @author Cedric Reichenbach
 * 
 */
public class Doodler {

	@Inject
	ScratchFactory scratchFactory;

	private Tag body;

	private HtmlRenderer htmlRenderer;

	private final boolean debugMode = true;

	private int level;

	private IndexedObjectStorage clickables;

	/**
	 * Creates a new Doodler for visualizing objects 1 Doodler = 1 window
	 */
	protected Doodler() {
		body = new Tag("body");
		clickables = new IndexedObjectStorage();
		htmlRenderer = new HtmlRenderer();
	}

	/**
	 * Visualizes any Object, either using draw Method of the object itself (if
	 * existing) or does a default drawing.
	 * 
	 * @param Object
	 *            not null
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

		// System.out.println(htmlDocument.toString());

		// for testing
		if (debugMode)
			openInBrowser(htmlDocument.toString());

		sendHtmlToEclipsePlugin(htmlDocument);

		// htmlRenderer.render(htmlDocument.toString());
	}

	private void sendHtmlToEclipsePlugin(HtmlDocument htmlDocument) {
		try {
			SimonClient client = new SimonClient();
			client.sendHtml(htmlDocument.toString(), clickables);
			client.stop();
		} catch (Exception e) {
			System.out
					.println("DoodleDebug: Failed to send html to eclipse plugin.");
			e.printStackTrace();
		}
	}

	public void renderInlineInto(Object object, Tag tag) {
		this.renderInline(object, tag, true);
	}

	public void renderInlineIntoWithoutClassName(Object object, Tag tag) {
		this.renderInline(object, tag, false);
	}

	private void renderInline(Object object, Tag tag, boolean withClassName) {
		if (object == null) {
			object = new NullObject();
		}
		level++; // TODO: smarter, nicer solution for this
		addClass(tag, "rendering");
		Scratch scratch = scratchFactory.create(object);
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

	private void openInBrowser(String html) {
		try {
			File file = new File(System.getProperty("user.dir")
					+ "/tempfiles/output.html");
			new File(file.getParent()).mkdirs();
			FileWriter fw = new FileWriter(file.getPath());
			BufferedWriter buf = new BufferedWriter(fw);
			buf.write(html);
			buf.close();

			URI uri = file.toURI();
			Desktop.getDesktop().browse(uri);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}