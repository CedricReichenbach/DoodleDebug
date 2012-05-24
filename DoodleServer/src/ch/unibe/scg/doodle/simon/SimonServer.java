package ch.unibe.scg.doodle.simon;

import java.io.IOException;
import java.net.UnknownHostException;

import org.eclipse.swt.widgets.Display;

import ch.unibe.scg.doodle.views.HtmlShow;
import client.IndexedObjectStorage;
import de.root1.simon.Lookup;
import de.root1.simon.Registry;
import de.root1.simon.Simon;
import de.root1.simon.annotation.SimonRemote;
import de.root1.simon.exceptions.NameBindingException;

@SimonRemote(value = { SimonServerInterface.class })
public class SimonServer implements SimonServerInterface {

	/**
	 * Official IANA port for SIMON
	 */
	private static final int PORT = 4753;
	private Registry registry;
	private SimonClientInterface client;
	private Lookup lookup;

	public static SimonServer instance;

	public SimonServer() throws UnknownHostException, IOException,
			NameBindingException {
		this.registry = Simon.createRegistry(PORT);
		registry.bind("DoodleServer", this);

		instance = this;
	}

	@Override
	public void showHtml(String html, IndexedObjectStorage storage) {
		System.out.println("Server: Received html code.");

		Runnable htmlShow = new HtmlShow(html, storage);
		Display.getDefault().syncExec(htmlShow);
	}

	public void stop() {
		registry.unbind("DoodleServer");
		registry.stop();
		if (client != null)
			lookup.release(client);
	}

	@Override
	public void showHtml(String html) {
		this.showHtml(html, null);
	}

	@Override
	public void clientOnline() {
		System.out
				.println("Client seems to be online now, let's check that...");
		try {
			this.lookup = Simon.createNameLookup("localhost", PORT + 1);
			this.client = (SimonClientInterface) lookup.lookup("DoodleClient");
		} catch (Exception e) {
			System.out.println("Could not find client on desired port.");
			e.printStackTrace();
		}
	}

	public void subRenderObject(int id) {
		client.subRender(id);
	}

}
