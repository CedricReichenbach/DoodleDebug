package ch.unibe.scg.doodle;

import java.io.IOException;

import ch.unibe.scg.doodle.simon.SimonClient;
import de.root1.simon.exceptions.EstablishConnectionFailed;
import de.root1.simon.exceptions.LookupFailedException;
import de.root1.simon.exceptions.NameBindingException;

public class DoodleClient {
	private SimonClient simonClient;

	private static DoodleClient instance = null;

	public static DoodleClient instance() {
		if (instance == null) {
			instance = new DoodleClient();
		}
		return instance;
	}

	protected DoodleClient() {
		try {
			this.simonClient = new SimonClient();
		} catch (LookupFailedException | EstablishConnectionFailed
				| IOException | NameBindingException e) {
			throw new RuntimeException(e);
		}
	}

	public void sendToServer(Object o) {
		simonClient.sendObject(o);
	}

	public void sendToServer(Object o, Object[] os) {
		simonClient.sendObjects(o, os);
	}
}
