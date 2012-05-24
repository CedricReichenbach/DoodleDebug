package app_example.ownDrawMethod;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

import javax.imageio.ImageIO;

import ch.unibe.scg.doodle.api.DoodleCanvas;
import ch.unibe.scg.doodle.api.Doodleable;

public class CopyOfPerson implements Doodleable, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private final String name;
	private final PhoneNumber number;
	private byte[] image;

	/**
	 * 
	 * @param image
	 *            in PNG format
	 */
	public CopyOfPerson(String name, PhoneNumber number, byte[] image) {
		this.name = name;
		this.number = number;
		this.image = image;
	}

	public String toString() {
		return "Person(" + name + ", " + number.toString() + ", "
				+ image.length + ")";
	}

	@Override
	public void drawOn(DoodleCanvas c) throws IOException {
		c.drawImage(this.image, "image/png");
		c.newColumn();
		c.draw(name);
		c.newLine();
		c.draw(number);
	}

	@Override
	public void drawSmallOn(DoodleCanvas c) throws IOException {
		InputStream in = new ByteArrayInputStream(image);
		BufferedImage image = ImageIO.read(in);
		c.draw(image);

	}

	public byte[] getImage() {
		return this.image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}
}