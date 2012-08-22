package ch.unibe.scg.doodle.simon;

import java.io.IOException;

import ch.unibe.scg.doodle.plugins.RenderingPlugin;

import com.google.inject.Module;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.ConversionException;

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

	public void stop() {
		lookup.release(server);
		registry.unbind("DoodleClient");
		registry.stop();
	}

	@Override
	public void sendObject(Object object) {
		try {
			String objectAsXML = xstream.toXML(object);
			server.renderObject(objectAsXML);
		} catch (ConversionException e) {
			server.couldNotSend(object.getClass().getCanonicalName());
		}
	}

	@Override
	public void sendObjects(Object object, Object[] objects) {
		String objectAsXML = xstream.toXML(object);
		String objectArrAsXML = xstream.toXML(objects);
		server.renderObjects(objectAsXML, objectArrAsXML);
	}

	public void clearOutput() {
		server.clearOutput();
	}

	public void firstRun() {
		server.firstRun();
	}

	public void addModules(RenderingPlugin... plugins) {
		String pluginsAsXML = xstream.toXML(plugins);
		server.addModules(pluginsAsXML);
	}

}
