package app;

import java.awt.Color;

import visualization.Doodler;

public class Bubble {

	private Color color;

	public Bubble() {
		this.color = new Color((int) (Math.random() * 255),
				(int) (Math.random() * 255), (int) (Math.random() * 255));
	}

	public Bubble(Color color) {
		this.color = color;
	}

	public String toString() {
		return "Bubble (" + this.color + ")";
	}
	
	public void draw(Doodler d) {
		d.draw(this.color);
	}

}
