package demo;

import java.io.IOException;

import demo.model.Directory;
import demo.util.DemoUtil;

public class DemoMain {

	private static final int MAX_CONTACTS = 10;

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		Directory directory = new Directory();

		while (directory.numberOfContacts() < MAX_CONTACTS) {
			directory.addContact(DemoUtil.randomContact());
		}

		System.out.println(directory);
	}
}
