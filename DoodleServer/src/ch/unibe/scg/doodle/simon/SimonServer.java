package ch.unibe.scg.doodle.simon;

import java.io.IOException;
import java.net.UnknownHostException;


import de.root1.simon.Registry;
import de.root1.simon.Simon;
import de.root1.simon.annotation.SimonRemote;
import de.root1.simon.exceptions.NameBindingException;

@SimonRemote(value = { SimonServerInterface.class })
public class SimonServer implements SimonServerInterface {

	private Registry registry;

	public SimonServer(int port) throws UnknownHostException, IOException,
			NameBindingException {
		this.registry = Simon.createRegistry(port);
		registry.bind("DoodleServer", this);
	}

	@Override
	public void ShowHtml(String html) {
		// TODO display in eclipse browser
		System.out.println("Server: Received html code.");
	}

}
