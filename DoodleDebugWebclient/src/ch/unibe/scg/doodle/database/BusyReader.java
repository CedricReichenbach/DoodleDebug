package ch.unibe.scg.doodle.database;

import java.util.Timer;
import java.util.TimerTask;

import ch.unibe.scg.doodle.Doodler;
import ch.unibe.scg.doodle.jetty.websocket.WebSocketSupervisor;
import ch.unibe.scg.doodle.server.DoodleServer;
import ch.unibe.scg.doodle.util.Pair;

/**
 * Repeatedly checks database for new doodles.
 * 
 * @author cedric
 * 
 */
public class BusyReader {

	private DoodleDatabase database;
	private Timer timer;
	private boolean firstRunOver;

	/**
	 * 
	 * @param updateInterval
	 *            (in ms)
	 */
	public BusyReader(DoodleDatabase database, int updateInterval) {
		this.database = database;

		firstRunOver = false;

		timer = new Timer();
		timer.scheduleAtFixedRate(new DoodleCheckTask(), 0, updateInterval);
	}

	class DoodleCheckTask extends TimerTask {
		public void run() {
			for (Pair<String, String> pair : database.loadNewDoodles()) {
				if (!firstRunOver) {
					DoodleServer.instance().firstRun();
					Doodler.instance(); // XXX: Hack to assure initiation
					firstRunOver = true;
				}

				WebSocketSupervisor.executeJavascript(pair.second);
				WebSocketSupervisor.executeJavascript(pair.first);
			}
		}
	}
}
