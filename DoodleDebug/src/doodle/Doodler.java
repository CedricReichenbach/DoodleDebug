package doodle;

import html_generator.Tag;
import view.HtmlRenderer;

/**
 * Class used for visualizing any Object
 * 
 * @author Cedric Reichenbach
 * 
 */
public class Doodler {
	
	private static final Doodler doodler = new Doodler();

	/**
	 * Implement singleton pattern.
	 * 
	 * @return Singleton Doodler instance
	 */
	public static Doodler instance() {
		return doodler;
	}

	/**
	 * Creates a new Doodler for visualizing objects 1 Doodler = 1 window
	 */
	protected Doodler() {
	}

	/**
	 * Visualizes any Object, either using draw Method of the object itself (if
	 * existing) or does a default drawing.
	 * 
	 * @param Object
	 *            o
	 */
	public void visualize(Object o) {
		Tag html = new Tag("html");
		new Scratch(o).drawWhole(html);
		// TODO: in one frame
		HtmlRenderer.render(html.toString());
		System.out.println(html);
	}

}