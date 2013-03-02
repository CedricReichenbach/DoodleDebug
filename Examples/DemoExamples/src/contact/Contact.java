package contact;

import java.awt.Image;

import ch.unibe.scg.doodle.api.DoodleCanvas;
import ch.unibe.scg.doodle.api.Doodleable;

public class Contact implements Doodleable {

	private String name;
	private Address address;
	private Image image;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public Contact(String name, Address address, Image image) {
		this.name = name;
		this.address = address;
		this.image = image;
	}
	
	@Override
	public void doodleOn(DoodleCanvas c) {
		c.draw(image);
		c.newColumn();
		c.draw(name);
		c.newLine();
		c.draw(address);
	}
	
	@Override
	public void summarizeOn(DoodleCanvas c) {
		c.draw(name);
	}

}
