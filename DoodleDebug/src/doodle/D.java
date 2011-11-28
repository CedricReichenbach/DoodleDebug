package doodle;

public class D {
	
	/**
	 * Code shugar for D.raw-ing any Object similar to System.out.println
	 * @param Object o
	 */
	public static void raw(Object o) {
		Doodler.instance().visualize(o);
	}
	
	/**
	 * Just redirects to D.raw
	 * @param Object o
	 */
	public static void draw(Object o) {
		raw(o);
	}
	
}
