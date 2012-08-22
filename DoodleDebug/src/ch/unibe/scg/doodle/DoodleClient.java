package ch.unibe.scg.doodle;

import java.io.IOException;

import ch.unibe.scg.doodle.simon.SimonClient;
import de.root1.simon.exceptions.EstablishConnectionFailed;
import de.root1.simon.exceptions.LookupFailedException;
import de.root1.simon.exceptions.NameBindingException;

class DoodleClient {
	private SimonClient simonClient;

	private static DoodleClient instance = null;

	public static DoodleClient instance() {
		if (instance == null) {
			instance = new DoodleClient();
			instance.firstRun();
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

	void sendToServer(Object o) {
		simonClient.sendObject(o);
	}

	void sendToServer(Object o, Object[] os) {
		simonClient.sendObjects(o, os);
	}

	void clearOutput() {
		simonClient.clearOutput();
	}

	protected void firstRun() {
		simonClient.firstRun();
	}
}
