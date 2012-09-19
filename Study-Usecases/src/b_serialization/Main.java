package b_serialization;

import ch.unibe.scg.doodle.Doo;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Address address = new Address("Green Mile 23", +41311234567L, "Bern");
		Contact contact = new Contact("Hans Hirschkuh", address);

		Doo.dle(contact.getAddress());
		
		SerializingUtil.serialize(contact);
		Contact after = SerializingUtil.deSerialize();

		Doo.dle(contact.getAddress(), after.getAddress());

		check(contact.equals(after));
	}

	private static void check(boolean valid) {
		if (!valid)
			throw new AssertionError();
	}

}
