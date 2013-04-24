package demo_scg.model;


public class Address {
	private String street;
	private int zipCode;
	private String city;
	private String country;

	public Address(String street, int zipCode, String city, String country) {
		super();
		this.street = street;
		this.zipCode = zipCode;
		this.city = city;
		this.country = country;
	}

	public String getStreet() {
		return street;
	}

	public int getZipCode() {
		return zipCode;
	}

	public String getCity() {
		return city;
	}

	public String getCountry() {
		return country;
	}

	// @Override
	// public void doodleOn(DoodleCanvas c) {
	// c.draw(street);
	// c.newLine();
	// c.draw(zipCode);
	// c.draw(city);
	// c.newLine();
	// c.draw(country);
	// }
	//
	// @Override
	// public void summarizeOn(DoodleCanvas c) {
	// c.draw(city);
	// }
}