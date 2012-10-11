package ch.unibe.scg.doodle.simon;

import java.io.IOException;
import java.util.Collection;

import ch.unibe.scg.doodle.plugins.RenderingPlugin;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.ConversionException;
import com.thoughtworks.xstream.core.TreeMarshaller.CircularReferenceException;
import com.thoughtworks.xstream.io.json.JettisonMappedXmlDriver;

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
	/**
	 * In JSON format
	 */
	private XStream jstream;
	/**
	 * IN XML format
	 */
	private XStream xstream;

	public SimonClient() throws LookupFailedException,
			EstablishConnectionFailed, IOException, NameBindingException {
		this.lookup = Simon.createNameLookup("localhost", PORT);
		server = (SimonServerInterface) lookup.lookup("DoodleServer");

		this.jstream = new XStream(new JettisonMappedXmlDriver());
		jstream.setMode(XStream.NO_REFERENCES);

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
			String objectAsXML = jstream.toXML(object);
			server.renderObject(objectAsXML, false);
		} catch (CircularReferenceException e) {
			// JSON problems -> try in XML
			String objectAsXML = xstream.toXML(object);
			server.renderObject(objectAsXML, true);
		} catch (ConversionException e) {
			showConversionError(object, e);
		}
	}

	@Override
	public void sendObjects(Object object, Object[] objects) {
		try {
			String objectAsXML = jstream.toXML(object);
			String objectArrAsXML = jstream.toXML(objects);
			server.renderObjects(objectAsXML, objectArrAsXML, false);
		} catch (CircularReferenceException e) {
			// JSON problems -> try in XML
			String objectAsXML = xstream.toXML(object);
			String objectArrAsXML = xstream.toXML(objects);
			server.renderObjects(objectAsXML, objectArrAsXML, true);
		} catch (ConversionException e) {
			showConversionError(object, e);
		}
	}

	void showConversionError(Object object, ConversionException e) {
		System.err.println("DoodleDebug: Could not serialize object ("
				+ object.getClass().getCanonicalName() + ") for sending");
		System.err.println("cause: " + e.getMessage());
		server.couldNotSend(object.getClass().getCanonicalName());
	}

	public void clearOutput() {
		server.clearOutput();
	}

	public void firstRun() {
		server.firstRun();
	}

	public void addPlugins(Collection<RenderingPlugin> plugins) {
		String pluginsAsXML = jstream.toXML(plugins);
		server.addPlugins(pluginsAsXML);
	}

}
