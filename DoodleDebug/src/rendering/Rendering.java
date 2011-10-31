package rendering;

import view.DoodleCanvas;

import doodle.Doodler;
import doodle.Scratch;

/**
 * Contains information of how an object should be rendered
 * 
 * @author Cedric Reichenbach
 * 
 */
public interface Rendering {
	/**
	 * for Objects with no user information about desired rendering structure
	 * 
	 * @param Object
	 *            o
	 */
	public void render(Object o, DoodleCanvas c, Doodler d);

	/**
	 * for Objects with user-given information about desired rendering structure
	 * 
	 * @param Scratch
	 *            top
	 */
	public void render(Scratch top, DoodleCanvas c, Doodler d);
}
