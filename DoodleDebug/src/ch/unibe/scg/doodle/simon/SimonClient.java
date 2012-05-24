package ch.unibe.scg.doodle.simon;

import java.io.IOException;
import java.net.UnknownHostException;

import ch.unibe.scg.doodle.IndexedObjectStorage;

import de.root1.simon.Lookup;
import de.root1.simon.Registry;
import de.root1.simon.Simon;
import de.root1.simon.annotation.SimonRemote;
import de.root1.simon.exceptions.EstablishConnectionFailed;
import de.root1.simon.exceptions.LookupFailedException;
import de.root1.simon.exceptions.NameBindingException;

@SimonRemote(value = { SimonClientInterface.class })
public class SimonClient implements SimonClientInterface {

	/**
	 * Official IANA port for SIMON
	 */
	private static final int PORT = 4753;
	private Lookup lookup;
	private SimonServerInterface server;
	private Registry registry;

	public SimonClient() throws LookupFailedException,
			EstablishConnectionFailed, IOException, NameBindingException {
		this.lookup = Simon.createNameLookup("localhost", PORT);
		server = (SimonServerInterface) lookup.lookup("DoodleServer");

		// create a registry for client too (for calls server -> client)
		registry = Simon.createRegistry(PORT + 1);
		registry.bind("DoodleClient", this);
		server.clientOnline();
	}

	@Override
	public void sendHtml(String html, IndexedObjectStorage storage) {
		server.showHtml(html); // TODO: transfer storage to plugin
	}

	public void stop() {
		lookup.release(server);
		registry.unbind("DoodleClient");
		registry.stop();
	}

	@Override
	public void subRender(int id) {
		// TODO Auto-generated method stub
		System.out.println("I'm the client, trying to subrender: " + id);
	}

}
