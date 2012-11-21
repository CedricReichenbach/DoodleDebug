package ch.unibe.scg.doodle.simon;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

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

	private static final String INFO_FILE_NAME = "connection-info";

	/**
	 * Official IANA port for SIMON
	 */
	private static final int PORT = 4753;
	private Lookup lookup;
	private SimonServerInterface server;
	private Registry registry;
	// /**
	// * In JSON format
	// */
	// private XStream jstream;
	/**
	 * IN XML format
	 */
	private XStream xstream;

	public SimonClient() throws LookupFailedException,
			EstablishConnectionFailed, IOException, NameBindingException {
		this.lookup = Simon.createNameLookup("localhost", PORT);
		String connectionId = findConnectionId();
		server = (SimonServerInterface) lookup.lookup(connectionId);

		// this.jstream = new XStream(new JettisonMappedXmlDriver());
		// jstream.setMode(XStream.NO_REFERENCES);

		this.xstream = new XStream();
	}

	private String findConnectionId() {
		File tempDir = FileUtil.mainTempDir();
		String content = FileUtil
				.readFromFile(new File(tempDir, INFO_FILE_NAME));

		String current;
		try {
			// FIXME: not necessarily correct, probably need to use stack trace
			current = new File(this.getClass().getResource(".").toURI())
					.getAbsolutePath();
		} catch (URISyntaxException e) {
			throw new RuntimeException(e);
		}

		String[] parts = content.split("\n");
		Iterator<String> iterator = Arrays.asList(parts).iterator();
		while (iterator.hasNext()) {
			String path = iterator.next();
			if (current.contains(path))
				return iterator.next();
			else
				iterator.next(); // wrong id
		}

		throw new RuntimeException("No fitting connection info found");
	}

	public void stop() {
		lookup.release(server);
		registry.unbind("DoodleClient");
		registry.stop();
	}

	@Override
	public void sendObject(Object object) {
		// Explanation: see method below
		// try {
		// String objectAsXML = jstream.toXML(object);
		// server.renderObject(objectAsXML, false);
		// } catch (CircularReferenceException e) {
		// JSON problems -> try in XML
		try {
			String objectAsXML = xstream.toXML(object);
			server.renderObject(objectAsXML, true);
		} catch (ConversionException e) {
			showConversionError(object, e);
		}
	}

	@Override
	public void sendObjects(Object object, Object[] objects) {
		// XXX: JSON Problems: Arrays lose their order, references are
		// impossible
		// try {
		// String objectAsXML = jstream.toXML(object);
		// String objectArrAsXML = jstream.toXML(objects);
		// server.renderObjects(objectAsXML, objectArrAsXML, false);
		// } catch (CircularReferenceException e) {
		// JSON problems -> try in XML
		try {
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
		String pluginsAsXML = xstream.toXML(plugins);
		server.addPlugins(pluginsAsXML);
	}

}
