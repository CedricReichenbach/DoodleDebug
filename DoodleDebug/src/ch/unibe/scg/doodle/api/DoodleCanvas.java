package ch.unibe.scg.doodle.api;

/**
 * A virtual canvas for the user to draw objects onto.<br>
 * It provides a cursor-like API, where the (imaginary) cursor is on top left
 * initially and always goes to the right of the lastly printed object.
 * 
 * @author Cedric Reichenbach
 * 
 */
public interface DoodleCanvas {

	/**
	 * Visualizes any Object inside the caller, either using draw Method of the
	 * object itself (if existing) or does a default drawing. Should only be
	 * called from another draw Method.
	 * 
	 * @param Object
	 *            o
	 */
	public abstract void draw(Object o);

	/**
	 * Jump to a new line with the virtual cursor, staying in the same column.
	 */
	public abstract void newLine();

	/**
	 * Jump to a new column with the virtual cursor, i.e. create a new column on
	 * the right side of the last one.
	 */
	public abstract void newColumn();

}
