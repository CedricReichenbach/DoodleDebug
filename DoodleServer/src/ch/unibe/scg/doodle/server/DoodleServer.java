package ch.unibe.scg.doodle.server;

import client.IndexedObjectStorage;
import ch.unibe.scg.doodle.D;

public class DoodleServer {
	private static DoodleServer instance;

	public static DoodleServer instance() {
		if (instance == null) {
			return new DoodleServer();
		}
		return instance;
	}

	protected DoodleServer() {

	}

	private IndexedObjectStorage storage;

	public void setStorage(IndexedObjectStorage storage) {
		this.storage = storage;
	}

	public void drawObjectWithID(int id) {
		if (this.storage == null) {
			D.raw("Sorry, don't know that object.");
			return;
		}
		Object o = this.storage.get(id);
		D.raw(o);
	}

}
