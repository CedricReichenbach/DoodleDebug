package ch.unibe.scg.doodle.simon;

public interface SimonServerInterface {
	public void renderObject(String objectAsXML, boolean xml);

	public void renderObjects(String objectAsXML, String objectArrayAsXML, boolean xml);

	public void clearOutput();

	public void firstRun();

	public void couldNotSend(String canonicalName);

	public void addPlugins(String pluginsAsXML);
}
