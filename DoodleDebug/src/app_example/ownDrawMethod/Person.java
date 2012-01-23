package app_example.ownDrawMethod;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import doodle.Drawable;
import doodle.Scratch;

public class Person implements Drawable {

	private final String name;
	private final PhoneNumber number;
	private final byte[] image;

	/**
	 * 
	 * @param image
	 *            in PNG format
	 */
	public Person(String name, PhoneNumber number, byte[] image) {
		this.name = name;
		this.number = number;
		this.image = image;
	}

	public String toString() {
		return name + ", " + number.toString();
	}

	@Override
	public void drawOn(Scratch s) throws IOException {
		InputStream in = new ByteArrayInputStream(image);
		BufferedImage image = null;
		image = ImageIO.read(in);

		s.draw(image);
		s.newColumn();
		s.draw(name);
		s.newLine();
		s.draw(number);
	}

	@Override
	public void drawSmallOn(Scratch s) throws IOException {
		InputStream in = new ByteArrayInputStream(image);
		BufferedImage image = ImageIO.read(in);
		s.draw(image);

	}
}