package sandbox;

import java.io.IOException;
import de.root1.simon.Lookup;
import de.root1.simon.Simon;
import de.root1.simon.annotation.SimonRemote;
import de.root1.simon.exceptions.EstablishConnectionFailed;
import de.root1.simon.exceptions.LookupFailedException;
import de.root1.simon.exceptions.SimonRemoteException;

@SimonRemote(value = {ClientInterface.class})
public class Client implements ClientInterface {

	private ServerInterface server;
	private Lookup lookup;
	
	public Client() throws SimonRemoteException, IOException, EstablishConnectionFailed, LookupFailedException {
		this.lookup = Simon.createNameLookup("localhost", 58800);
		server = (ServerInterface) lookup.lookup("Server");
	    server.sayHello(this);
	}
	
	@Override
	public void hello() throws SimonRemoteException {
		System.out.println("Oh, hai. This is Client.");
//		lookup.release(server);
//		System.exit(0);
	}

}
