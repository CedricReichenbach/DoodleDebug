package ch.unibe.scg.doodle.server.simon;

import java.io.IOException;
import java.net.UnknownHostException;

import ch.unibe.scg.doodle.D;
import com.thoughtworks.xstream.XStream;

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
	private XStream xstream;

	public static SimonServer instance;

	public SimonServer() throws UnknownHostException, IOException,
			NameBindingException {
		this.registry = Simon.createRegistry(PORT);
		registry.bind("DoodleServer", this);

		instance = this;

		this.xstream = new XStream();
		xstream.setClassLoader(DoodleClientWorkspace.getClientClassLoader());
	}

	// @Override
	// public void showHtml(String html, String storageAsXML) {
	// System.out.println("Server: Received html code.");
	//
	// IndexedObjectStorage storage = storageFromXML(storageAsXML);
	//
	// Runnable htmlShow = new HtmlShow(html, storage);
	// Display.getDefault().syncExec(htmlShow);
	// }

	public void stop() {
		registry.unbind("DoodleServer");
		registry.stop();
		if (client != null)
			lookup.release(client);
	}

	// @Override
	// public void showHtml(String html) {
	// this.showHtml(html, null);
	// }

	@Override
	public void renderObject(String objectAsXML) {
		Object o = xstream.fromXML(objectAsXML);
		D.raw(o);
	}

	@Override
	public void renderObjects(String objectAsXML, String objectArrayAsXML) {
		Object o = xstream.fromXML(objectAsXML);
		Object[] os = (Object[]) xstream.fromXML(objectArrayAsXML); // XXX Check
																	// this!
		D.raw(o, os);
	}

}
