package doodle;

import java.util.ArrayList;
import java.util.List;

/**
 * Class used for visualizing any Object
 * @author Cedric Reichenbach
 *
 */
public class Doodler {
	
	/**
	 * represents top Scratch for main object to be drawn on
	 */
	private Scratch topScratch;
	
	/**
	 * Creates a new Doodler for visualizing objects
	 * 1 Doodler = 1 window
	 */
	public Doodler() {
		this.topScratch = new Scratch();
	}
	
	/**
	 * Visualizes any Object, either using draw Method of the object itself (if existing) or
	 * does a default drawing.
	 * @param Object o
	 */
	public void visualize(Object o) {
		topScratch.drawWhole(o);
	}
	
}