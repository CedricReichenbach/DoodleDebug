package ch.unibe.scg.doodle.simon;

public interface SimonServerInterface {
	public void showHtml(String html); // XXX same problem as in server

	public void showHtml(String html, String storageAsXML);
}
