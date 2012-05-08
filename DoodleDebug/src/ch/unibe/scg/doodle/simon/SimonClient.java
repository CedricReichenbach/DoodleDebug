package ch.unibe.scg.doodle.simon;

import java.net.UnknownHostException;

import ch.unibe.scg.doodle.IndexedObjectStorage;

import de.root1.simon.Lookup;
import de.root1.simon.Simon;
import de.root1.simon.annotation.SimonRemote;
import de.root1.simon.exceptions.EstablishConnectionFailed;
import de.root1.simon.exceptions.LookupFailedException;

@SimonRemote(value = { SimonClientInterface.class })
public class SimonClient implements SimonClientInterface {

	/**
	 * Official IANA port for SIMON
	 */
	private static final int PORT = 4753;
	private Lookup lookup;
	private SimonServerInterface server;

	public SimonClient() throws LookupFailedException,
			EstablishConnectionFailed, UnknownHostException {
		this.lookup = Simon.createNameLookup("localhost", PORT);
		server = (SimonServerInterface) lookup.lookup("DoodleServer");
	}

	@Override
	public void sendHtml(String html, IndexedObjectStorage storage) {
		server.showHtml(html); // TODO: transfer storage to plugin
	}

	public void stop() {
		lookup.release(server);
	}

}
