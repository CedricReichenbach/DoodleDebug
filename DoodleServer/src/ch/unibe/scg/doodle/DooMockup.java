package ch.unibe.scg.doodle;

import ch.unibe.scg.doodle.helperClasses.DoodleList;
import ch.unibe.scg.doodle.inject.DoodleModule;

import com.google.inject.Guice;
import com.google.inject.Injector;

/**
 * Mockup on server side for <code>Doo</code> on client side.
 * 
 * @author Cedric Reichenbach
 * 
 */
public class DooMockup {

	static Injector injector;

	static Injector injectorInstance() {
		if (injector == null) {
			injector = Guice.createInjector(new DoodleModule());
		}
		return injector;
	}

	public static void resetInjector() {
		injector = null;
	}

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
		Doodler doodler = injectorInstance().getInstance(Doodler.class);

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
