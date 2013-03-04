package demo.model;

import java.util.ArrayList;
import java.util.Collection;

public class Directory {
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
//		String result = "";
//		result += "Directory: (";
//		for (Contact contact : contacts) {
//			result += contact+", ";
//		}
//		result += ")";
	}
	
}
