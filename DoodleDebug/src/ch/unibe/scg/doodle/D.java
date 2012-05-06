package ch.unibe.scg.doodle;

import ch.unibe.scg.doodle.helperClasses.DoodleList;
import ch.unibe.scg.doodle.inject.DoodleModule;

import com.google.inject.Guice;
import com.google.inject.Injector;

public class D {

	static Injector injector;

	static Injector injectorInstance() {
		if (injector == null) {
			injector = Guice.createInjector(new DoodleModule());
		}
		return injector;
	}

	/**
	 * Code sugar for D.raw-ing any Object similar to System.out.println
	 * 
	 * @param Object
	 *            o
	 */
	public static void raw(Object o) {
		Object[] emptyArr = {};
		raw(o, emptyArr);
	}

	/**
	 * Code sugar for D.raw-ing any Object similar to System.out.println
	 * 
	 * @param Object
	 *            o
	 */
	public static void raw(Object o, Object... objects) {
		Doodler doodler = injectorInstance().getInstance(Doodler.class);

		if (objects.length == 0) {
			doodler.visualize(o);
			return;
		}

		DoodleList list = new DoodleList();
		list.add(o);
		for (Object object : objects) {
			list.add(object);
		}
		doodler.visualize(list);
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
