package sandbox;

import java.io.IOException;
import java.net.UnknownHostException;

import de.root1.simon.exceptions.EstablishConnectionFailed;
import de.root1.simon.exceptions.LookupFailedException;
import de.root1.simon.exceptions.NameBindingException;
import de.root1.simon.exceptions.SimonRemoteException;

public class Main {

	/**
	 * @param args
	 * @throws IOException 
	 * @throws NameBindingException 
	 * @throws UnknownHostException 
	 * @throws LookupFailedException 
	 * @throws EstablishConnectionFailed 
	 * @throws SimonRemoteException 
	 */
	public static void main(String[] args) throws UnknownHostException, NameBindingException, IOException, SimonRemoteException, EstablishConnectionFailed, LookupFailedException {
		System.out.println("Starting server...");
		new Server();
		System.out.println("Server started");
		System.out.println("Starting client...");
		new Client();
		System.out.println("Client started");
	}

}
