package ch.unibe.scg.doodle.plugins;

import ch.unibe.scg.doodle.plugins.html.Tag;


/**
 * Contains information of how an object should be rendered
 * 
 * @author Cedric Reichenbach
 * 
 */
public interface Rendering<T> {
	/**
	 * for Objects with no user information about desired rendering structure
	 * 
	 * @param Object
	 *            o
	 */
	public void render(T object, Tag tag) throws DoodleRenderException;

	/**
	 * Produces a smaller, space-saving rendering of the give object.
	 * 
	 * @param object
	 * @param tag
	 * @throws DoodleRenderException
	 */
	public void renderSmall(T object, Tag tag) throws DoodleRenderException;
}
