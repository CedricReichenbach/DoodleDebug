package doodle.util;

import java.awt.Image;
import java.io.IOException;
import java.net.URL;
import java.util.Random;

import javax.imageio.ImageIO;

import doodle.model.Address;
import doodle.model.Contact;
import doodle.model.PhoneNumber;

public class DemoUtil {
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
	private static final String[] countries = { "Switzerland", "USA", "Japan",
			"Australia", "Finland", "Uganda", "India", "Brazil" };

	public static Contact randomContact() throws IOException {
		String firstName = randomElementOf(firstNames);
		String lastName = randomElementOf(lastNames);
		Address address = randomAddress();
		PhoneNumber phoneNumber = new PhoneNumber("+41 23", "456 78 90");
		URL url = DemoUtil.class.getResource(randomElementOf(images) + ".png");
		Image image = ImageIO.read(url);
		Contact contact = new Contact(firstName + " " + lastName, address,
				phoneNumber, image);
		return contact;
	}

	private static Address randomAddress() {
		String street = randomElementOf(streets);
		String city = randomElementOf(cities);
		String country = randomElementOf(countries);
		return new Address(street, random(9999), city, country);
	}

	private static String randomElementOf(String[] array) {
		int size = array.length;
		return array[random(size)];
	}

	private static int random(int max) {
		return new Random().nextInt(max);
	}
}
