package doodleable_example;

import ch.unibe.scg.doodle.Doo;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Address address = new Address("Dead End 23", 3012, "Bern");
		Contact contact = new Contact("Endo Anaconda", address);
		Doo.dle(contact);
	}

}
