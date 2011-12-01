package rendering;

import html_generator.Tag;
import view.DoodleCanvas;

import doodle.Doodler;
import doodle.Scratch;

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
	public void render(T object, Tag tag);
}
