package demo_scg.model;

import java.util.ArrayList;
import java.util.Collection;

import ch.unibe.scg.doodle.api.DoodleCanvas;
import ch.unibe.scg.doodle.api.Doodleable;

public class Directory implements Doodleable {
	private Collection<Contact> contacts;

	public Directory() {
		this.contacts = new ArrayList<Contact>();
	}

	public void addContact(Contact contact) {
		this.contacts.add(contact);
	}

	public int numberOfContacts() {
		return contacts.size();
	}

	public String toString() {
		return contacts.toString();
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
		c.draw("Directory with " + contacts.size() + " contacts");
	}

}
