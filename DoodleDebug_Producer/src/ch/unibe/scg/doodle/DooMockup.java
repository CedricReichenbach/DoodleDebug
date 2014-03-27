package ch.unibe.scg.doodle;

import ch.unibe.scg.doodle.helperClasses.DoodleList;

/**
 * Mockup on server side for <code>Doo</code> on client side.
 * 
 * TODO: Is not necessary anymore since we have fat clients now.
 * 
 * @author Cedric Reichenbach
 * 
 */
public class DooMockup {

	/**
	 * Code sugar for Doo.dle-ing any Object similar to System.out.println
	 * 
	 * @param Object
	 *            o
	 */
	public static void dle(Object o) {
		Object[] emptyArr = {};
		dle(o, emptyArr);
	}

	/**
	 * Code sugar for Doo.dle-ing any Object similar to System.out.println
	 * 
	 * @param Object
	 *            o
	 */
	public static void dle(Object o, Object... objects) {
		Doodler doodler = Doodler.instance();

		if (objects.length == 0) {
			doodler.visualize(o);
			return;
		}

		DoodleList list = new DoodleList();
		list.add(o);
		for (int i = 0; i < objects.length; i++) {
			list.add(objects[i]);
		}
		doodler.visualize(list);
	}

}
