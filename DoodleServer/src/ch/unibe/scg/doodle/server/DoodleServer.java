package ch.unibe.scg.doodle.server;

import org.eclipse.swt.widgets.Display;

import ch.unibe.scg.doodle.DMockup;
import ch.unibe.scg.doodle.IndexedObjectStorage;
import ch.unibe.scg.doodle.server.views.HtmlShow;

public class DoodleServer {
	private static DoodleServer instance;

	public static DoodleServer instance() {
		if (instance == null) {
			instance = new DoodleServer();
		}
		return instance;
	}

	protected DoodleServer() {

	}

	private IndexedObjectStorage storage;

	public void setStorage(IndexedObjectStorage storage) {
		this.storage = storage;
		System.out.println(this.storage);
	}

	public void drawObjectWithID(int id) {
		if (this.storage == null) {
			DMockup.raw("Sorry, don't know that object.");
			return;
		}
		Object o = this.storage.get(id);
		DMockup.raw(o);
	}

	public void clearOutput() {
		DMockup.resetInstance();
		Runnable emptyShow = new HtmlShow("");
		Display.getDefault().asyncExec(emptyShow);
	}
}
