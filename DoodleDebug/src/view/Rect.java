package view;

import java.awt.Color;
import java.awt.geom.Point2D;

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

	public Point2D.Double getPosition() {
		return position;
	}

	public void setPosition(Point2D.Double position) {
		this.position = position;
	}

	public double getWidth() {
		return width;
	}

	public void setWidth(double width) {
		this.width = width;
	}

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
	}

	public Color getFillColor() {
		return fillColor;
	}

	public void setFillColor(Color fillColor) {
		this.fillColor = fillColor;
	}

}
