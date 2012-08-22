package ch.unibe.scg.doodle;

public class Doo {

	/**
	 * Visualize any object with DoodleDebug, usage is similar to
	 * System.out.println(). To visualize multiple objects in line (beside each
	 * other) using one call, use {@link Doo#dle(Object, Object...)} (varargs). <br>
	 * Name is code sugar to build the word Doo.dle.
	 * 
	 * @param Object
	 *            o
	 */
	public static void dle(Object o) {
		DoodleClient.instance().sendToServer(o);
	}

	/**
	 * Same functionality as {@link Doo#dle(Object) Doo.dle()} with the option
	 * to visualize multiple objects beside each other with one method call.
	 * 
	 * @param Object
	 *            o
	 */
	public static void dle(Object o, Object... objects) {
		DoodleClient.instance().sendToServer(o, objects);
	}
}
