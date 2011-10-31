package view;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Defines a virtual canvas with no defined size, just for arrangement of
 * geometrical elements, used for visualizing an object. Coordinates are always
 * in [0,1] for both dimensions.
 * 
 * @author Cedric Reichenbach
 * 
 */
public class DoodleCanvas {

	ArrayList<Rect> rects;
	ArrayList<DoodleCanvas> cans;
	HashMap<DoodleCanvas, Rect> canvasMap;

	public DoodleCanvas() {
		this.rects = new ArrayList<Rect>();
		this.cans = new ArrayList<DoodleCanvas>();
		this.canvasMap = new HashMap<DoodleCanvas, Rect>();
	}

	public void drawRect(Rect rect) {
		this.rects.add(rect);
	}

	public void drawCanvas(DoodleCanvas canvas, Rect rect) {
		this.cans.add(canvas);
		this.canvasMap.put(canvas, rect);
	}

}
