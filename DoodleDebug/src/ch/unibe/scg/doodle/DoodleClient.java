package ch.unibe.scg.doodle;

import java.util.Collection;

import ch.unibe.scg.doodle.plugins.RenderingPlugin;
import ch.unibe.scg.doodle.simon.SimonClient;

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
			// } catch (LookupFailedException | EstablishConnectionFailed
			// | IOException | NameBindingException e) { // TODO when going to
			// JRE 1.7: Multicatch
		} catch (RuntimeException e) {
			throw e;
		} catch (Exception e) {
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

	public void addPlugins(Collection<RenderingPlugin> plugins) {
		simonClient.addPlugins(plugins);
	}
}
