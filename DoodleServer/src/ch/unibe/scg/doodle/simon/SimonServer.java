package ch.unibe.scg.doodle.simon;

import java.io.IOException;
import java.net.UnknownHostException;

import org.eclipse.swt.widgets.Display;

import client.IndexedObjectStorage;

import ch.unibe.scg.doodle.views.HtmlShow;
import de.root1.simon.Registry;
import de.root1.simon.Simon;
import de.root1.simon.annotation.SimonRemote;
import de.root1.simon.exceptions.NameBindingException;

@SimonRemote(value = { SimonServerInterface.class })
public class SimonServer implements SimonServerInterface {

	private Registry registry;

	public SimonServer(int port) throws UnknownHostException, IOException,
			NameBindingException {
		this.registry = Simon.createRegistry(port);
		registry.bind("DoodleServer", this);
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
	}

	@Override
	public void showHtml(String html) {
		this.showHtml(html, null);
	}

}
