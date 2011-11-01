package rendering;

import java.awt.Color;
import java.awt.geom.Point2D;

import view.DoodleCanvas;
import view.Rect;

public class DefaultRendering implements Rendering<Object> {

	@Override
	public DoodleCanvas render(Object object) {
		// TODO Auto-generated method stub
		DoodleCanvas canvas = new DoodleCanvas();
		Rect rect = new Rect(new Point2D.Double(.1,.1), .8, .8);
		rect.setFillColor(Color.RED);
		canvas.drawRect(rect);
		return canvas;
	}


}
