package b_serialization;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Address address = new Address("Green Mile 23", 3012, "Bern");
		Contact contact = new Contact("Hans Hirschkuh", address);
		
		SerializingUtil.serialize(contact);
		Contact after = SerializingUtil.deSerialize();
		
		check(contact.equals(after));
	}

	private static void check(boolean valid) {
		if (!valid)
			throw new AssertionError();
	}

}
