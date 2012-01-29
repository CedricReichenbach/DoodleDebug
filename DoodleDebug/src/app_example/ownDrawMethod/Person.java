package app_example.ownDrawMethod;

import java.io.Serializable;

import doodle.Drawable;
import doodle.Scratch;

public class Person implements Serializable, Drawable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private final String name;
	private final PhoneNumber phoneNumber;
	private byte[] image;

	/**
	 * 
	 * @param image
	 *            in PNG format
	 */
	public Person(String name, PhoneNumber number, byte[] image) {
		this.name = name;
		this.phoneNumber = number;
		this.image = image;
	}

	public String toString() {
		return "Person("+name + ", " + phoneNumber.toString()+", "+image.length+")";
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
		s.draw(phoneNumber);
	}

	@Override
	public void drawSmallOn(Scratch s) throws Exception {
		// TODO Auto-generated method stub
		
	}
}