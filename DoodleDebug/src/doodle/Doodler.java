package doodle;

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
		// TODO: Initialize rendering and displaying
	}
	
	/**
	 * Visualizes any Object, either using draw Method of the object itself (if existing) or
	 * does a default drawing.
	 * @param Object o
	 */
	public void draw(Object o) {
		// TODO: Use o's draw method, if existing or else, use drawDefault
	}
	
	/**
	 * Makes a default drawing for objects that don't implement their own draw method.
	 * @param Object o
	 */
	private void drawDefault(Object o) {
		// TODO: draw default rendering of o
	}
	
	/**
	 * Visualizes any Object with few details, either using drawSmall Method of the object itself (if existing) or
	 * does a default drawing.
	 * @param Object o
	 */
	public void drawSmall(Object o) {
		// TODO: Use o's drawSmall method, if existing or else, use drawSmallDefault
	}
	
	/**
	 * Makes a default drawing with few details for objects that don't implement their own drawSmall method.
	 * @param Object o
	 */
	private void drawSmallDefault(Object o) {
		// TODO: draw default rendering of o
	}
}