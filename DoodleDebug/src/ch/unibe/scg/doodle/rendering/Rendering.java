package ch.unibe.scg.doodle.rendering;

import ch.unibe.scg.doodle.htmlgen.Tag;

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
	 * Produces a smaller, space-saving rendering of the given object.
	 * 
	 * @param object
	 * @param tag
	 * @throws DoodleRenderException
	 */
	public void renderSimplified(T object, Tag tag) throws DoodleRenderException;
}
