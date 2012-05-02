package ch.unibe.scg.doodle;

import java.awt.Desktop;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;

import javax.inject.Inject;

import ch.unibe.ch.scg.htmlgen.Attribute;
import ch.unibe.ch.scg.htmlgen.Attributes;
import ch.unibe.ch.scg.htmlgen.Tag;
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

	/**
	 * Creates a new Doodler for visualizing objects 1 Doodler = 1 window
	 */
	protected Doodler() {
		body = new Tag("body");
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
		assert (o != null);
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
		int port = 58801;
		try {
			SimonClient client = new SimonClient(port);
			client.sendHtml(htmlDocument.toString());
			client.stop();
		} catch (Exception e) {
			System.out.println("Failed to send html to eclipse plugin.");
			e.printStackTrace();
		}
	}

	public void renderInlineInto(Object object, Tag tag) {
		level++; // TODO: smarter, nicer solution for this
		addClass(tag, "rendering");
		Tag div = new Tag("div");
		Scratch scratch = scratchFactory.create(object);
		scratch.addCSSClass("level" + level);
		scratch.drawWholeWithName(div);
		addClass(div, scratch.getClassAttribute());
		tag.add(div);
		level--;
	}

	public void renderInlineIntoWithoutClassName(Object object, Tag tag) {
		level++;
		addClass(tag, "rendering");
		Tag div = new Tag("div");
		Scratch scratch = scratchFactory.create(object);
		scratch.addCSSClass("level" + level);
		scratch.drawWhole(div);
		addClass(div, scratch.getClassAttribute());
		tag.add(div);
		level--;
	}

	private void addClass(Tag tag, String className) {
		Attributes attributes = tag.getAttributes(); // XXX
		for (Attribute a : attributes) {
			String attName = a.getAttribute();
			if (attName.equals("class")) {
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