package doodle;

import java.util.ArrayList;
import java.util.List;

import view.DoodleCanvas;

/**
 * Class used for visualizing any Object
 * @author Cedric Reichenbach
 *
 */
public class Doodler {
	
	/**
	 * Creates a new Doodler for visualizing objects
	 * 1 Doodler = 1 window
	 */
	public Doodler() {
	}
	
	/**
	 * Visualizes any Object, either using draw Method of the object itself (if existing) or
	 * does a default drawing.
	 * @param Object o
	 */
	public void visualize(Object o) {
		DoodleCanvas canvas = new Scratch(o).getCanvas();
		// TODO
	}
	
}