package visualization;

import java.awt.Dimension;

public class D {
	public static void raw(Object o) {
		// set up window
		BasicWindow window = new BasicWindow("Visualisation", new Dimension(
				700, 400), 2);
		
		// set up Drawer
		Doodler doodler = new Doodler(window);
		doodler.draw(o);
		
		// set up resize listener
		window.addComponentListener(new ResizeListener(doodler, o));
	}
}
