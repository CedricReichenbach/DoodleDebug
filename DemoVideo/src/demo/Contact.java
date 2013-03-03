package demo;

import java.awt.Image;

public class Contact {
	private String name;
	private Address adress;
	private String phoneNumber;
	private Image picture;
	
	public Contact(String name, Address adress, String phoneNumber,
			Image picture) {
		super();
		this.name = name;
		this.adress = adress;
		this.phoneNumber = phoneNumber;
		this.picture = picture;
	}

	public String getName() {
		return name;
	}

	public Address getAdress() {
		return adress;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public Image getPicture() {
		return picture;
	}
}
