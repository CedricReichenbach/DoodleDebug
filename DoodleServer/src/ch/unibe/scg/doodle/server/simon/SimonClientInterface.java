package ch.unibe.scg.doodle.server.simon;


public interface SimonClientInterface {
	public void sendObject(String objectAsXML);
	public void sendObjects(String objectAsXML, String objectArrayAsXML);
}
