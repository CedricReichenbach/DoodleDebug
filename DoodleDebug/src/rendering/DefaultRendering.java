package rendering;

import java.awt.Color;
import java.awt.geom.Point2D;
import view.DoodleCanvas;
import view.Rect;

public class DefaultRendering implements Rendering<Object> {

	@Override
	public DoodleCanvas render(Object object) {
		// TODO: not finished...
		DoodleCanvas canvas = new DoodleCanvas();

		// frame
		Rect mainRect = new Rect(new Point2D.Double(0.01, 0.01), .8, .98);
		canvas.drawRect(mainRect);

		// class type
		canvas.drawText(object.getClass().getSimpleName(), new Point2D.Double(
				.02, .02));
		
		// fields
		// TODO:
		
		return canvas;
	}

}
