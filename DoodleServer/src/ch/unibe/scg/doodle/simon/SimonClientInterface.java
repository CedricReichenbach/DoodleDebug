package ch.unibe.scg.doodle.simon;

import ch.unibe.scg.doodle.IndexedObjectStorage;

public interface SimonClientInterface {
	public void sendHtml(String html, IndexedObjectStorage storage);
	
	public void subRender(int id);
}
