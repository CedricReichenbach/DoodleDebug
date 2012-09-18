package b_serialization;

public class Address {
	protected String street;
	protected int postalCode;
	protected String city;

	public Address(String street, int postalCode, String city) {
		this.street = street;
		this.postalCode = postalCode;
		this.city = city;
	}

	public boolean equals(Address other) {
		return this.street.equals(other.street)
				&& this.postalCode == other.postalCode
				&& this.city.equals(other.city);
	}

	public Address getCopy() {
		return new Address(this.street, this.postalCode, this.city);
	}

}
