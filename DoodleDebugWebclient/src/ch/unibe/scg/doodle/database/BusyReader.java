package ch.unibe.scg.doodle.database;

import java.util.Timer;
import java.util.TimerTask;

import ch.unibe.scg.doodle.Doodler;
import ch.unibe.scg.doodle.jetty.websocket.DoodleSocket;
import ch.unibe.scg.doodle.server.DoodleServer;
import ch.unibe.scg.doodle.util.Pair;

/**
 * Repeatedly checks database for new doodles.
 * 
 * @author cedric
 * 
 */
public class BusyReader {

	private DoodleSocket socket;
	private DoodleDatabase database;
	private Timer timer;
	private boolean firstRunOver;
	private DoodleServer doodleServer;

	/**
	 * 
	 * @param doodleServer 
	 * @param updateInterval
	 *            (in ms)
	 */
	public BusyReader(DoodleSocket socket, DoodleServer doodleServer, DoodleDatabase database,
			int updateInterval) {
		this.socket = socket;
		this.doodleServer = doodleServer;
		this.database = database;

		firstRunOver = false;

		timer = new Timer();
		timer.scheduleAtFixedRate(new DoodleCheckTask(), 0, updateInterval);
	}

	class DoodleCheckTask extends TimerTask {
		public void run() {
			for (Pair<String, String> pair : database.loadNewDoodles()) {
				if (!firstRunOver) {
					doodleServer.firstRun();
					Doodler.instance(); // XXX: Hack to assure initiation
					firstRunOver = true;
				}

				socket.executeJSOnClient(pair.second);
				socket.executeJSOnClient(pair.first);
			}
		}
	}
}
