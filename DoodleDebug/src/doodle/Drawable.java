package doodle;

/**
 * Should be imlemented, if an Object wants to define its own "draw" and "drawSmall" methods, similar to "toString".
 * @author Cedric Reichenbach
 *
 */
public interface Drawable {
	
	/**
	 * Equivalent method to toString, but for drawing.
	 * Defines how to draw self object using methods of received Doodler object (similar to System.out.println)
	 * @param Doodler d
	 */
	public void drawOn(Scratch s);
	
	/**
	 * Similar to "draw(d)", used for small drawings with less detail.
	 * @param Doodler d
	 */
	public void drawSmallOn(Scratch s);
	
}
