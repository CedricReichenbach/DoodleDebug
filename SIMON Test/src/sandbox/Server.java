package sandbox;

import java.io.IOException;
import java.net.UnknownHostException;

import de.root1.simon.Registry;
import de.root1.simon.Simon;
import de.root1.simon.annotation.SimonRemote;
import de.root1.simon.exceptions.NameBindingException;
import de.root1.simon.exceptions.SimonRemoteException;

@SimonRemote(value = {ServerInterface.class})
public class Server implements ServerInterface {

	private Registry registry;
	
	public Server() throws NameBindingException, UnknownHostException, IOException {
		this.registry = Simon.createRegistry(58800);
		registry.bind("Server", this);
	}

	@Override
	public void sayHello(ClientInterface client) throws SimonRemoteException {
		System.out.println("Hello, this is Server.");
		client.hello();
//		registry.unbind("Server");
//		registry.stop();
//		System.exit(0);
	}

}
