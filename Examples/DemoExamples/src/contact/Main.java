package contact;

import java.util.Random;

import ch.unibe.scg.doodle.Doo;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		AddressBook addressBook = generateAddressBook();
		Doo.dle(addressBook);
	}

	private static AddressBook generateAddressBook() {
		AddressBook addressBook = new AddressBook();
		while (addressBook.numberOfContacts() < 12) {
			addressBook.addContact(randomContact());
		}
		return addressBook;
	}
	
	private static final String[] firstNames = {"John", "Benjamin", "Hannah", "Gregory", "Rose", "Amanda"};
	private static final String[] lastNames = {"Derendinger", "Doe", "Franklin", "Goldsworthy", "Duff", "Montana"};
	private static final String[] streets = {"Länggasse 12", "La Rambla 55b", "Down Street 0", "Hai Ming Way"};
	private static final String[] cities = {"Bern", "Thun", "Ajaccio", "Moskow", "New York", "Guatemala City"};

	private static Contact randomContact() {
		String firstName = randomElementOf(firstNames);
		String lastName = randomElementOf(lastNames);
		String street = randomElementOf(streets);
		String city = randomElementOf(cities);
		Address address = new Address(street, random(9999), city);
		Contact contact = new Contact(firstName + " " + lastName, address);
		return contact;
	}

	private static String randomElementOf(String[] array) {
		int size = array.length;
		return array[random(size)];
	}
	
	private static int random(int max) {
		return new Random().nextInt(max);
	}

}
