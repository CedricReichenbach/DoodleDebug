package ch.unibe.scg.doodle.api;

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
	 * Visualizes any Object with few details, either using drawSmall Method of
	 * the object itself (if existing) or does a default drawing.
	 * 
	 * @param Object
	 *            o
	 */
	public abstract void drawSmall(Object o);

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
