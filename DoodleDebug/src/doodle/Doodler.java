package doodle;

import html_generator.Tag;

import javax.inject.Inject;

import rendering.RenderingRegistry;

import com.google.inject.Guice;
import com.google.inject.Injector;

/**
 * Class used for visualizing any Object
 * 
 * @author Cedric Reichenbach
 * 
 */
public class Doodler {

	/**
	 * Creates a new Doodler for visualizing objects 1 Doodler = 1 window
	 */
	public Doodler() {
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
		System.out.println(html);
	}

}