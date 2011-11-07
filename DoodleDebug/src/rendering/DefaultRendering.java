package rendering;

import java.awt.Color;
import java.awt.geom.Point2D;

import doodle.Scratch;
import view.DoodleCanvas;
import view.Rect;

public class DefaultRendering implements Rendering<Object> {

	@Override
	public DoodleCanvas render(Object object) {
		return new Scratch(new DrawableWrapper(object)).getCanvas();
	}

}
