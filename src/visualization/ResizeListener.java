package visualization;

import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

public class ResizeListener implements ComponentListener {

	private Drawer drawer;
	private Object o;

	public ResizeListener(Drawer drawer, Object o) {
		this.drawer = drawer;
		this.o = o;
	}

	@Override
	public void componentHidden(ComponentEvent e) {
		// do nothing
	}

	@Override
	public void componentMoved(ComponentEvent e) {
		// do nothing
	}

	@Override
	public void componentResized(ComponentEvent e) {
		drawer.draw(o); // TODO: won't work for maximizing and not always with
						// other resizing. Annotation: Maximizing is async!
		}

	@Override
	public void componentShown(ComponentEvent e) {
		// do nothing
	}

}
