package doodle.model;

import java.awt.Image;

public class Contact {
	private String name;
	private Address address;
	private PhoneNumber phoneNumber;
	private Image picture;

	public Contact(String name, Address address, PhoneNumber phoneNumbers,
			Image picture) {
		super();
		this.name = name;
		this.address = address;
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

	public String toString() {
		return "\n("  + name + ", " + address + ", "
				+ phoneNumber + ")";
	}
}
