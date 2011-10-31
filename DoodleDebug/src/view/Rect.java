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

	private Float position;
	private float width;
	private float height;
	private Color fillColor;

	public Rect(Point2D.Float position, float width, float height) {
		this.position = position;
		this.width = width;
		this.height = height;
		this.fillColor = Color.WHITE;
	}

}
