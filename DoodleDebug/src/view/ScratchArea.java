package view;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public class ScratchArea {

	private Point2D.Double upperLeft, lowerRight;

	/**
	 * Defines ScratchArea by corners.
	 * 
	 * @param Point
	 *            upperLeft
	 * @param Point
	 *            lowerRight
	 */
	public ScratchArea(Point2D.Double upperLeft, Point2D.Double lowerRight) {
		this.upperLeft = upperLeft;
		this.lowerRight = lowerRight;
	}

	/**
	 * Defines ScratchArea by edge boundaries
	 * @param double up
	 * @param double left
	 * @param double low
	 * @param double right
	 */
	public ScratchArea(double up, double left, double low, double right) {
		this.upperLeft = new Point2D.Double(up, left);
		this.upperLeft = new Point2D.Double(low, right);
	}

}
