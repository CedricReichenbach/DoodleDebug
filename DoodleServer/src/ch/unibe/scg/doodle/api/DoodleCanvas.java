package ch.unibe.scg.doodle.api;

import java.io.IOException;
import java.util.List;

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
	
	public abstract void drawImage(byte[] image, String mimeType) throws IOException;

	public abstract void newLine();

	public abstract void newColumn();

	// TODO: should not be exposed to user
	public abstract List<List<List<Object>>> getColumns();

}
