package ch.unibe.scg.doodle.jetty.websocket;

import ch.unibe.scg.doodle.database.BusyReader;
import ch.unibe.scg.doodle.database.DoodleDatabase;
import ch.unibe.scg.doodle.server.DoodleServer;
import ch.unibe.scg.doodle.util.Pair;

/**
 * This class contains and handles state-relevant data for clients on
 * server-side.
 * 
 * @author Cedric Reichenbach
 *
 */
public class ClientCustodian {

	private DoodleSocket socket;
	private DoodleDatabase database;

	public ClientCustodian(DoodleSocket doodleSocket) {
		this.socket = doodleSocket;

		this.database = new DoodleDatabase();
	}

	public void listFullHistory() {
		for (Pair<String, String> javascripts : database.loadNewDoodles()) {
			socket.executeJSOnClient(javascripts.first);
			socket.executeJSOnClient(javascripts.second);
		}
		new BusyReader(socket, database, 1000);
	}

	public void lightboxClosed() {
		// TODO: DoodleServer instance for each client
		DoodleServer.instance().lightboxClosed();
	}

	public void cutFromStack(int toCutOff) {
		// TODO: DoodleServer instance for each client
		String javascript = DoodleServer.instance().cutoffFromStack(toCutOff);
		socket.executeJSOnClient(javascript);
	}

	public void drawInLightbox(int id) {
		// TODO: DoodleServer instance for each client
		String javascript = DoodleServer.instance().drawObjectWithID(id);
		socket.executeJSOnClient(javascript);
	}
}