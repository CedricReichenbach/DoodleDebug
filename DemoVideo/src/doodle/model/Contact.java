package doodle.model;

import java.awt.Image;

import ch.unibe.scg.doodle.api.DoodleCanvas;
import ch.unibe.scg.doodle.api.Doodleable;

public class Contact implements Doodleable{
	private String name;
	private Address address;
	private PhoneNumber phoneNumber;
	private Image picture;
	
	public Contact(String name, Address adress, PhoneNumber phoneNumbers,
			Image picture) {
		super();
		this.name = name;
		this.address = adress;
		this.phoneNumber = phoneNumbers;
		this.picture = picture;
	}

	public String getName() {
		return name;
	}

	public Address getAdress() {
		return address;
	}

	public PhoneNumber getPhoneNumbers() {
		return phoneNumber;
	}

	public Image getPicture() {
		return picture;
	}

	@Override
	public void doodleOn(DoodleCanvas c) {
		c.draw(picture);
		c.newColumn();
		c.draw(name);
		c.newLine();
		c.draw(phoneNumber);
		c.newColumn();
		c.draw(address);
	}

	@Override
	public void summarizeOn(DoodleCanvas c) {
		c.draw(name);
	}
}
