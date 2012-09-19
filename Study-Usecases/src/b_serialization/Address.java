package b_serialization;


public class Address {
	private String street;
	protected long phoneNumber;
	private String city;

	public Address(String street, long phoneNumber, String city) {
		this.street = street;
		this.phoneNumber = phoneNumber;
		this.city = city;
	}

	public boolean equals(Address other) {
		return this.street.equals(other.street)
				&& this.phoneNumber == other.phoneNumber
				&& this.city.equals(other.city);
	}

	public Address getCopy() {
		return new Address(this.street, this.phoneNumber, this.city);
	}
}
