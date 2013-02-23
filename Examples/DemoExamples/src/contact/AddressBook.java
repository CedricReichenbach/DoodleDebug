package contact;

import java.util.ArrayList;
import java.util.List;

import ch.unibe.scg.doodle.api.DoodleCanvas;
import ch.unibe.scg.doodle.api.Doodleable;

public class AddressBook implements Doodleable {
	private List<Contact> contacts;

	public AddressBook() {
		this.contacts = new ArrayList<Contact>();
	}

	public void addContact(Contact contact) {
		contacts.add(contact);
	}

	public int numberOfContacts() {
		return contacts.size();
	}
	
	@Override
	public void doodleOn(DoodleCanvas c) {
		for (Contact contact : contacts) {
			c.draw(contact);
			c.newLine();
		}
	}

	@Override
	public void summarizeOn(DoodleCanvas c) {
		c.draw("AddressBook with " + numberOfContacts() + " entries");
	}
}
