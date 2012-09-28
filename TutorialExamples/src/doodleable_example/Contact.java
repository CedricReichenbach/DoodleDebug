package doodleable_example;

import ch.unibe.scg.doodle.api.DoodleCanvas;
import ch.unibe.scg.doodle.api.Doodleable;

public class Contact implements Doodleable {

	private String name;
	private Address address;
	
	public Contact(String name, Address address) {
		this.name = name;
		this.address = address;
	}
	
	@Override
	public void doodleOn(DoodleCanvas c) {
		c.draw(name);
		c.newLine();
		c.draw(address);
	}
	
	@Override
	public void summarizeOn(DoodleCanvas c) {
		c.draw(name);
	}

}
