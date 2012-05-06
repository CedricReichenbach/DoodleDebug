package ch.unibe.scg.doodle.simon;

import client.IndexedObjectStorage;

public interface SimonServerInterface {
	public void showHtml(String html); // XXX only while other one not working

	public void showHtml(String html, IndexedObjectStorage storage);
}
