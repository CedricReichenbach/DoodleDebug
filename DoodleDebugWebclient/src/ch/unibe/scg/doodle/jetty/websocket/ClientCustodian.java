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
	private DoodleServer doodleServer;

	public ClientCustodian(DoodleSocket doodleSocket) {
		this.socket = doodleSocket;

		this.doodleServer = new DoodleServer();
		this.database = new DoodleDatabase();
	}

	public void listFullHistory() {
		for (Pair<String, String> javascripts : database.loadNewDoodles()) {
			socket.executeJSOnClient(javascripts.first);
			socket.executeJSOnClient(javascripts.second);
		}
		new BusyReader(socket, doodleServer, database, 1000);
	}

	public void lightboxClosed() {
		doodleServer.lightboxClosed();
	}

	public void cutFromStack(int toCutOff) {
		String javascript = doodleServer.cutoffFromStack(toCutOff);
		socket.executeJSOnClient(javascript);
	}

	public void drawInLightbox(int id) {
		String javascript = doodleServer.drawObjectWithID(id);
		socket.executeJSOnClient(javascript);
	}
}
