package contact;

import ch.unibe.scg.doodle.api.DoodleCanvas;
import ch.unibe.scg.doodle.api.Doodleable;

public class Address implements Doodleable {
	private String street;
	private int postalCode;
	private String city;

	public Address(String street, int postalCode, String city) {
		this.street = street;
		this.postalCode = postalCode;
		this.city = city;
	}

	@Override
	public void doodleOn(DoodleCanvas c) {
		c.draw(street);
		c.newLine();
		c.draw(postalCode);
		c.draw(city);
	}

	@Override
	public void summarizeOn(DoodleCanvas c) {
		c.draw(city);
	}

	public String toString() {
		return street + "\n" + postalCode + " " + city;
	}
}
