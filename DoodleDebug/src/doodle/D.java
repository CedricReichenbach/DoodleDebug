package doodle;

import javax.inject.Inject;

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
	public static void raw(Object o) {
		Doodler doodler = injectorInstance().getInstance(Doodler.class);
		doodler.visualize(o);
	}
	
	/**
	 * Just redirects to D.raw
	 * @param Object o
	 */
	public static void draw(Object o) {
		raw(o);
	}
	
}
