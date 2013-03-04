package demo.model;


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
}