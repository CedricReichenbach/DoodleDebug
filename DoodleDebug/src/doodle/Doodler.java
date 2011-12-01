package doodle;

import javax.inject.Inject;
import javax.inject.Provider;

import html_generator.Attribute;
import html_generator.Tag;
import view.HtmlDocument;
import view.HtmlRenderer;

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
	 * @param Object
	 *            
	 */
	@SuppressWarnings("unchecked")
	public void visualize(Object o) {
		scratchFactory.create(o).drawWhole(body);
		
		// XXX: ugly workaround to identify renderings of this object type later
		Object appended = body.getLast();
		if (appended instanceof Tag) {
			((Tag) appended).getAttributes().add(new Attribute("id", o.getClass().getCanonicalName()));
		}
		
		body.add(Tag.br());
		HtmlDocument htmlDocument = new HtmlDocument();
		htmlDocument.setBody(body);
		htmlRenderer.render(htmlDocument.toString());
	}

}