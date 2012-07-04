package ch.unibe.scg.doodle;

public class D {

	/**
	 * Code sugar for D.raw-ing any Object similar to System.out.println
	 * 
	 * @param Object
	 *            o
	 */
	public static void raw(Object o) {

	}

	/**
	 * Code sugar for D.raw-ing any Object similar to System.out.println
	 * 
	 * @param Object
	 *            o
	 */
	public static void raw(Object o, Object... objects) {

	}

	/**
	 * Equivalent to D.raw(Object o)
	 * 
	 * @param Object
	 *            o
	 */
	public static void draw(Object o) {
		raw(o);
	}

	/**
	 * Equivalent to D.raw(Object o, Object... objects)
	 * 
	 * @param Object
	 *            o
	 */
	public static void draw(Object o, Object... objects) {
		raw(o, objects);
	}

}
