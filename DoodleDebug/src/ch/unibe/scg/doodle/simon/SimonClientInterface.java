package ch.unibe.scg.doodle.simon;

public interface SimonClientInterface {
	public void sendObject(Object object);

	public void sendObjects(Object object, Object[] objects);
	
	public void firstRun();
}
