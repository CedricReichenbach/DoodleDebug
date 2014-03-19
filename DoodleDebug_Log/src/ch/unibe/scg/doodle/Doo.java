package ch.unibe.scg.doodle;

/**
 * Stub for the {@link Doo#dle(Object) Doo.dle(..)} methods.
 * 
 * @author Cedric Reichenbach
 * 
 */
public final class Doo { // Don't delete this, it's needed here to be doodleable.

	/**
	 * This class should not be implemented.
	 */
	protected Doo() {

	}

	/**
	 * Visualize any object with DoodleDebug, usage is similar to
	 * System.out.println(). To visualize multiple objects in line (beside each
	 * other) using one call, use {@link Doo#dle(Object, Object...) Doo.dle(o1, o2, ...)} (varargs). <br>
	 * Name is code sugar to build the word Doo.dle.
	 * 
	 * @param Object
	 *            o
	 */
	public static void dle(Object o) {
//		DoodleClient.instance().sendToServer(o);
	}

	/**
	 * Same functionality as {@link Doo#dle(Object) Doo.dle(object)} with the option
	 * to visualize multiple objects beside each other with one method call.
	 * 
	 * @param Object
	 *            o
	 */
	public static void dle(Object o, Object... objects) {
//		DoodleClient.instance().sendToServer(o, objects);
	}
}
