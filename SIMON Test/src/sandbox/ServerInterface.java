package sandbox;



import de.root1.simon.exceptions.SimonRemoteException;

public interface ServerInterface {
	public void sayHello(ClientInterface client) throws SimonRemoteException;
}
