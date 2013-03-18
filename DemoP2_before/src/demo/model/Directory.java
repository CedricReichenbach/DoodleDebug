package demo.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import demo.util.IdFactory;

public class Directory {
	private Collection<Contact> contacts;
	private Map<Integer, Contact> contactsById;

	public Directory() {
		this.contacts = new ArrayList<Contact>();
		this.contactsById = new HashMap<Integer, Contact>();
	}

	public void addContact(Contact contact) {
		this.contacts.add(contact);
		this.contactsById.put(IdFactory.nextId(), contact);
	}

	public int numberOfContacts() {
		return contacts.size();
	}

	public String toString() {
		return contacts.toString();
	}

}
