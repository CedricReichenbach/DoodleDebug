package ch.unibe.scg.doodle.api;

/**
 * Should be imlemented, if an Object wants to define its own "draw" and
 * "drawSmall" methods, similar to "toString".
 * 
 * @author Cedric Reichenbach
 * 
 */
public interface Doodleable {

	/**
	 * Equivalent method to toString, but for doodling. Defines how to draw this
	 * object using methods of received DoodleCanvas object.
	 * 
	 * @param DoodleCanvas
	 *            c
	 */
	public void doodleOn(DoodleCanvas c);

	/**
	 * Similar to "doodleOn(d)", used for small drawings with less detail.
	 * 
	 * @param DoodleCanvas
	 *            c
	 */
	public void summarizeOn(DoodleCanvas c);

}
