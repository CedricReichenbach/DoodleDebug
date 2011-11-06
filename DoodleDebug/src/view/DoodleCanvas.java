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
	HashMap<DoodleCanvas, Rect> canvasMap;
	HashMap<String, Point2D.Double> textMap;

	public DoodleCanvas() {
		this.rects = new ArrayList<Rect>();
		this.canvasMap = new HashMap<DoodleCanvas, Rect>();
		this.textMap = new HashMap<String, Point2D.Double>();
	}

	public void drawRect(Rect rect) {
		this.rects.add(rect);
	}

	public void drawCanvas(DoodleCanvas canvas, Rect rect) {
		this.canvasMap.put(canvas, rect);
	}
	
	public void drawText(String string, Point2D.Double position) {
		this.textMap.put(string, position);
	}

	public ArrayList<Rect> getRects() {
		return rects;
	}

	public HashMap<DoodleCanvas, Rect> getCanvasMap() {
		return canvasMap;
	}

	public HashMap<String, Point2D.Double> getTextMap() {
		return textMap;
	}

}
