package ch.unibe.scg.doodle.api;



/**
 * Should be imlemented, if an Object wants to define its own "draw" and "drawSmall" methods, similar to "toString".
 * @author Cedric Reichenbach
 *
 */
public interface Doodleable {
	
	/**
	 * Equivalent method to toString, but for drawing.
	 * Defines how to draw self object using methods of received Doodler object (similar to System.out.println)
	 * @param Doodler d
	 */
	public void drawOn(DoodleCanvas c) throws Exception;
	
	/**
	 * Similar to "draw(d)", used for small drawings with less detail.
	 * @param Doodler d
	 * @throws Exception 
	 */
	public void drawSmallOn(DoodleCanvas c) throws Exception;
	
}
