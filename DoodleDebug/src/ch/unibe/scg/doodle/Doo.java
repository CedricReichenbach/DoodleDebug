package ch.unibe.scg.doodle;

public class Doo {

	/**
	 * Code sugar for Doo.dle-ing any Object similar to System.out.println
	 * 
	 * @param Object
	 *            o
	 */
	public static void dle(Object o) {
		DoodleClient.instance().sendToServer(o);
	}

	/**
	 * Code sugar for Doo.dle-ing any Object similar to System.out.println
	 * 
	 * @param Object
	 *            o
	 */
	public static void dle(Object o, Object... objects) {
		DoodleClient.instance().sendToServer(o, objects);
	}
}
