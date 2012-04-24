package ch.unibe.scg.doodle;

import com.google.inject.Guice;
import com.google.inject.Injector;

public class D {

	static Injector injector;
	
	static Injector injectorInstance() {
		if(injector == null) {
			injector = Guice.createInjector(new DoodleModule());
		}
		return injector;
	}
	
	/**
	 * Code sugar for D.raw-ing any Object similar to System.out.println
	 * @param Object o
	 */
	public static void raw(Object o, Object... objects) {
		Doodler doodler = injectorInstance().getInstance(Doodler.class);
		
		if (objects.length == 0) {
			doodler.visualize(o);
			return;
		}
		
		Object[] objArr = new Object[objects.length+1];
		objArr[0] = o;
		for (int i = 1; i < objArr.length; i++) {
			objArr[i] = objects[i-1];
		}
		doodler.visualize(objArr);
	}
	
	/**
	 * Equivalent to D.raw(Object o, Object... objects)
	 * @param Object o
	 */
	public static void draw(Object o, Object... objects) {
		raw(o, objects);
	}
	
}
