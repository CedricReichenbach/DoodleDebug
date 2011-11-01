package rendering;

import java.awt.geom.Point2D;

import view.DoodleCanvas;
import view.Rect;
import doodle.Doodler;
import doodle.Scratch;

public class ScratchRendering implements Rendering<Scratch> {

	@Override
	public DoodleCanvas render(Scratch scratch) {
		// TODO: only pseudo visualization now
		DoodleCanvas canvas = new DoodleCanvas();
		canvas.drawRect(new Rect(new Point2D.Double(.2, .2), .6, .6));
		for (Scratch s : scratch.getInner()) {
			// TODO: define rects
			// canvas.drawCanvas(s.getCanvas(), anyRect);
		}
		return canvas;
	}

}
