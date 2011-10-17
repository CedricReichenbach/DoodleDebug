package visualization;

import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

public class ResizeListener implements ComponentListener {

	private Doodler doodler;
	private Object o;

	public ResizeListener(Doodler doodler, Object o) {
		this.doodler = doodler;
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
		doodler.draw(o); // TODO: won't work for maximizing and not always with
						// other resizing. Annotation: Maximizing is async!
		}

	@Override
	public void componentShown(ComponentEvent e) {
		// do nothing
	}

}
