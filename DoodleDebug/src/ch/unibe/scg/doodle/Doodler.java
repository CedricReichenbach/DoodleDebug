package ch.unibe.scg.doodle;


import java.awt.Desktop;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;

import javax.inject.Inject;

import ch.unibe.ch.scg.htmlgen.Attribute;
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
	 * @param Object not null
	 *            
	 */
	@SuppressWarnings("unchecked")
	public void visualize(Object o) {
		assert (o != null);
		scratchFactory.create(o).drawWholeWithName(body);
		
		body.add(new Tag("hr","class=betweenDrawCalls"));
		HtmlDocument htmlDocument = new HtmlDocument();
		htmlDocument.setBody(body);
		
//		System.out.println(htmlDocument.toString());
		
		// for testing
//		openInBrowser(htmlDocument.toString());
		
		sentHtmlToEclipsePlugin(htmlDocument);
		
//		htmlRenderer.render(htmlDocument.toString());
	}

	private void sentHtmlToEclipsePlugin(HtmlDocument htmlDocument) {
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
		Tag div = new Tag("div");
		Scratch scratch = scratchFactory.create(object);
		scratch.drawWholeWithName(div);
		div.addAttribute(new Attribute("class", scratch.getClassAttribute()));
		tag.add(div);
	}

	public void renderInlineIntoWithoutClassName(Object object, Tag tag) {
		Tag div = new Tag("div");
		Scratch scratch = scratchFactory.create(object);
		scratch.drawWhole(div);
		div.addAttribute(new Attribute("class", scratch.getClassAttribute()));
		tag.add(div);
	}

	private void openInBrowser(String html) {
		try {
			File file = new File(System.getProperty("user.dir") + "/tempfiles/output.html");
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