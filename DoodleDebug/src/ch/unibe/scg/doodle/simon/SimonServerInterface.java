package ch.unibe.scg.doodle.simon;

public interface SimonServerInterface {
	public void renderObject(String objectAsXML);

	public void renderObjects(String objectAsXML, String objectArrayAsXML);
}
