package ch.unibe.scg.doodle.simon;

import java.io.IOException;

import com.thoughtworks.xstream.XStream;

import de.root1.simon.Lookup;
import de.root1.simon.Registry;
import de.root1.simon.Simon;
import de.root1.simon.annotation.SimonRemote;
import de.root1.simon.exceptions.EstablishConnectionFailed;
import de.root1.simon.exceptions.LookupFailedException;
import de.root1.simon.exceptions.NameBindingException;

@SimonRemote(value = { SimonClientInterface.class })
public class SimonClient implements SimonClientInterface {

	/**
	 * Official IANA port for SIMON
	 */
	private static final int PORT = 4753;
	private Lookup lookup;
	private SimonServerInterface server;
	private Registry registry;
	private XStream xstream;

	public SimonClient() throws LookupFailedException,
			EstablishConnectionFailed, IOException, NameBindingException {
		this.lookup = Simon.createNameLookup("localhost", PORT);
		server = (SimonServerInterface) lookup.lookup("DoodleServer");

		this.xstream = new XStream();
	}

	// @Override
	// public void sendHtml(String html, IndexedObjectStorage storage) {
	// String storageAsXML = storageToXML(storage);
	// server.showHtml(html, storageAsXML); // TODO: transfer storage to plugin
	// }

	// private String storageToXML(IndexedObjectStorage storage) {
	// XStream xstream = new XStream();
	// // xstream.alias("IndexedObjectStorage", IndexedObjectStorage.class);
	// return xstream.toXML(storage);
	// }

	public void stop() {
		lookup.release(server);
		registry.unbind("DoodleClient");
		registry.stop();
	}

	@Override
	public void sendObject(Object object) {
		String objectAsXML = xstream.toXML(object);
		server.renderObject(objectAsXML);
	}

	@Override
	public void sendObjects(Object object, Object[] objects) {
		String objectAsXML = xstream.toXML(object);
		String objectArrAsXML = xstream.toXML(objects);
		server.renderObjects(objectAsXML, objectArrAsXML);
	}

}
