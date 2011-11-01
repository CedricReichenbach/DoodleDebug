package view;

import java.awt.Color;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Float;

/**
 * Represents a rectangular in a DoodleCanvas. Contains information about
 * position, size and color.
 * 
 * @author Cedric Reichenbach
 * 
 */
public class Rect {

	private Point2D.Double position;
	private double width;
	private double height;
	private Color fillColor;

	public Rect(Point2D.Double position, double width, double height) {
		this.position = position;
		this.width = width;
		this.height = height;
		this.fillColor = Color.WHITE;
	}

	public Color getFillColor() {
		return fillColor;
	}

	public void setFillColor(Color fillColor) {
		this.fillColor = fillColor;
	}

}
