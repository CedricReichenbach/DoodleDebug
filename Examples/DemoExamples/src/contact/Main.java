package contact;

import java.awt.Image;
import java.io.IOException;
import java.net.URL;
import java.util.Random;

import javax.imageio.ImageIO;

import ch.unibe.scg.doodle.Doo;

public class Main {

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		AddressBook addressBook = generateAddressBook();
		Doo.dle(addressBook);
	}

	private static AddressBook generateAddressBook() throws IOException {
		AddressBook addressBook = new AddressBook();
		while (addressBook.numberOfContacts() < 12) {
			addressBook.addContact(randomContact());
		}
		return addressBook;
	}

	private static final String[] firstNames = { "John", "Benjamin", "Hannah",
			"Gregory", "Rose", "Amanda" };
	private static final String[] lastNames = { "Derendinger", "Doe",
			"Franklin", "Goldsworthy", "Duff", "Montana" };
	private static final String[] streets = { "Länggasse 12", "La Rambla 55b",
			"Down Street 0", "Hai Ming Way" };
	private static final String[] cities = { "Bern", "Thun", "Ajaccio",
			"Moskow", "New York", "Guatemala City" };
	private static final String[] images = { "baby-tux", "bird-green", "bird",
			"devil-green", "devil", "joker-blue", "joker", "smile-boy",
			"smile-boy-green", "troll" };

	private static Contact randomContact() throws IOException {
		String firstName = randomElementOf(firstNames);
		String lastName = randomElementOf(lastNames);
		String street = randomElementOf(streets);
		String city = randomElementOf(cities);
		Address address = new Address(street, random(9999), city);
		URL url = Main.class.getResource(randomElementOf(images) + ".png");
		Image image = ImageIO.read(url);
		Contact contact = new Contact(firstName + " " + lastName, address,
				image);
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
