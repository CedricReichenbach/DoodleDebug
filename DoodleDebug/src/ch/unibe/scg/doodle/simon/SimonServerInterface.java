package ch.unibe.scg.doodle.simon;

public interface SimonServerInterface {
	public void renderObject(String objectAsXML);

	public void renderObjects(String objectAsXML, String objectArrayAsXML);

	public void clearOutput();

	public void firstRun();

	public void couldNotSend(String canonicalName);

	public void addModules(String pluginsAsXML);
}
