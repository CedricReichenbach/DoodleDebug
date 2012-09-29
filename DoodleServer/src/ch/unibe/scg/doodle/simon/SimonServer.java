package ch.unibe.scg.doodle.simon;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Collection;

import ch.unibe.scg.doodle.DMockup;
import ch.unibe.scg.doodle.Doodler;
import ch.unibe.scg.doodle.plugins.RenderingPlugin;
import ch.unibe.scg.doodle.rendering.RenderingRegistry;
import ch.unibe.scg.doodle.server.DoodleClientWorkspace;
import ch.unibe.scg.doodle.server.DoodleServer;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.json.JettisonMappedXmlDriver;

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

		refreshClientClassloading();
	}

	/**
	 * Refresh remote classes by creating a new XStream and a new Classloader.
	 */
	public void refreshClientClassloading() {
		this.xstream = new XStream(new JettisonMappedXmlDriver());
		xstream.setMode(XStream.NO_REFERENCES);
		xstream.setClassLoader(DoodleClientWorkspace.getClientClassLoader());
	}

	public void stop() {
		registry.unbind("DoodleServer");
		registry.stop();
		if (client != null)
			lookup.release(client);
	}

	@Override
	public void renderObject(String objectAsXML) {
		Object o = xstream.fromXML(objectAsXML);
		DMockup.raw(o);
	}

	@Override
	public void renderObjects(String objectAsXML, String objectArrayAsXML) {
		Object o = xstream.fromXML(objectAsXML);
		Object[] os = (Object[]) xstream.fromXML(objectArrayAsXML);
		DMockup.raw(o, os);
	}

	@Override
	public void couldNotSend(String canonicalName) {
		Doodler.instance().couldNotRenderMessage(canonicalName);
	}

	@Override
	public void clearOutput() {
		DoodleServer.instance().clearOutput();
	}

	@Override
	public void firstRun() {
		DoodleServer.instance().firstRun();
	}

	@Override
	public void addPlugins(String pluginsAsXML) {
		Collection<RenderingPlugin> plugins = (Collection<RenderingPlugin>) xstream
				.fromXML(pluginsAsXML);
		RenderingRegistry.addPlugins(plugins);
	}

}
