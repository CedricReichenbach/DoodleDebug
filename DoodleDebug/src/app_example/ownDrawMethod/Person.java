package app_example.ownDrawMethod;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

import javax.imageio.ImageIO;

import doodle.Drawable;
import doodle.Scratch;

public class Person implements Serializable, Drawable {

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
	public Person(String name, PhoneNumber number, byte[] image) {
		this.name = name;
		this.number = number;
		this.image = image;
	}

	public String toString() {
		return "Person("+name + ", " + number.toString()+", "+image.length+")";
	}

	public byte[] getImage() {
		return this.image;
	}
	
	public void setImage(byte[] image) {
		this.image = image;
	}

	@Override
	public void drawOn(Scratch s) throws Exception {
		s.drawImage(image, "image/png");
		s.newColumn();
		s.draw(name);
		s.newLine();
		s.draw(number);
	}

	@Override
	public void drawSmallOn(Scratch s) throws Exception {
		// TODO Auto-generated method stub
		
	}
}