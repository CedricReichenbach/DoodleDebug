package ch.unibe.scg.doodle.simon;

import ch.unibe.scg.doodle.IndexedObjectStorage;

public interface SimonServerInterface {
	public void showHtml(String html); // XXX same problem as in server

	public void showHtml(String html, IndexedObjectStorage storage);

	public void clientOnline();
}
