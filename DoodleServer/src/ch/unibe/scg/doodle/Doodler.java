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
import ch.unibe.scg.doodle.server.views.HtmlShow;
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

	private Tag body;

	private final boolean debugMode = false;

	private int level;

	private IndexedObjectStorage clickables;

	private final File FILE = new File(System.getProperty("java.io.tmpdir") // Win7: C:\Users\<user>\AppData\Local\Temp
			+ "/doodledebug/output.html");

	/**
	 * Creates a new Doodler for visualizing objects 1 Doodler = 1 window
	 */
	protected Doodler() {
		body = new Tag("body");
		clickables = new IndexedObjectStorage();
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

		// for testing
		storeToFile(FILE, htmlDocument.toString());
		if (debugMode)
			openInBrowser(FILE);

		DoodleServer.instance().setStorage(clickables);
		Runnable htmlShow = new HtmlShow(htmlDocument.toString());
		Display.getDefault().syncExec(htmlShow);
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

	private void storeToFile(File file, String html) {
		try {
			new File(file.getParent()).mkdirs();
			FileWriter fw = new FileWriter(file.getPath());
			BufferedWriter buf = new BufferedWriter(fw);
			buf.write(html);
			buf.close();
		} catch (IOException e) {
			System.err.println("SERVER: Could not write into file " + file
					+ "\n" + e.getMessage());
		}
	}

	private void openInBrowser(File file) {
		URI uri = file.toURI();
		try {
			Desktop.getDesktop().browse(uri);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}