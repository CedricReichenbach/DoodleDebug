package b_serialization;

public class Contact {
	private String name;

	private Address address;

	public Contact(String name, Address address) {
		this.name = name;
		this.address = address;
	}

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

	public boolean equals(Contact other) {
		return this.name.equals(other.getName())
				&& this.address.equals(other.getAddress());
	}

	public Contact getCopy() {
		return new Contact(this.name, this.address.getCopy());
	}

}