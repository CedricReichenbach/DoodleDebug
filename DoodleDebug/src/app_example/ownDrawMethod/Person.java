package app_example.ownDrawMethod;

import java.io.Serializable;
import java.util.Arrays;

import ch.unibe.scg.doodle.api.DoodleCanvas;
import ch.unibe.scg.doodle.api.Doodleable;

public class Person implements Serializable, Cloneable, Doodleable {

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
		return "Person(" + name + ", " + phoneNumber + ", " + image.length
				+ ")";
	}

	public byte[] getImage() {
		return this.image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}

	public Object clone() {
		try {
			return super.clone();
		} catch (CloneNotSupportedException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(image);
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result
				+ ((phoneNumber == null) ? 0 : phoneNumber.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Person other = (Person) obj;
		if (!Arrays.equals(image, other.image))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (phoneNumber == null) {
			if (other.phoneNumber != null)
				return false;
		} else if (!phoneNumber.equals(other.phoneNumber))
			return false;
		return true;
	}

	@Override
	public void drawOn(DoodleCanvas c) throws Exception {
		c.drawImage(image, "image/png");
		c.newColumn();
		c.draw(name);
		c.newLine();
		c.draw(phoneNumber);
	}

	@Override
	public void drawSmallOn(DoodleCanvas c) throws Exception {
		// TODO Auto-generated method stub

	}

}