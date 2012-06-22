package ch.unibe.scg.doodle.simon;

import java.io.IOException;
import java.net.UnknownHostException;

import org.eclipse.swt.widgets.Display;

import com.thoughtworks.xstream.XStream;

import ch.unibe.scg.doodle.IndexedObjectStorage;
import ch.unibe.scg.doodle.views.HtmlShow;
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
	public void showHtml(String html, String storageAsXML) {
		System.out.println("Server: Received html code.");

		IndexedObjectStorage storage = storageFromXML(storageAsXML);

		Runnable htmlShow = new HtmlShow(html, storage);
		Display.getDefault().syncExec(htmlShow);
	}

	private IndexedObjectStorage storageFromXML(String storageAsXML) {
		XStream xstream = new XStream();
		xstream.setClassLoader(IndexedObjectStorage.class.getClassLoader()); // XXX This is weird, but needed...
		// xstream.alias("IndexedObjectStorage", IndexedObjectStorage.class);
		System.out.println(storageAsXML);
		return (IndexedObjectStorage) xstream.fromXML(storageAsXML);
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

	public void subRenderObject(int id) {
	}

}
