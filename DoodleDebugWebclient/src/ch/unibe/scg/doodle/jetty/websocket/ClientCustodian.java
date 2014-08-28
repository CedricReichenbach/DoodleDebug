package ch.unibe.scg.doodle.jetty.websocket;

import ch.unibe.scg.doodle.database.DoodleDatabase;
import ch.unibe.scg.doodle.util.Pair;

/**
 * This class contains and handles state-relevant data for clients on
 * server-side.
 * 
 * @author Cedric Reichenbach
 *
 */
public class ClientCustodian {

	private DoodleSocket doodleSocket;
	private DoodleDatabase database;

	public ClientCustodian(DoodleSocket doodleSocket) {
		this.doodleSocket = doodleSocket;

		this.database = new DoodleDatabase();
	}

	public void listFullHistory() {
		for (Pair<String, String> javascripts : database.loadNewDoodles()) {
			doodleSocket.executeJSOnClient(javascripts.first);
			doodleSocket.executeJSOnClient(javascripts.second);
		}
	}
}
