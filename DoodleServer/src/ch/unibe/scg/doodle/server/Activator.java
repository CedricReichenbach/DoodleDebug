package ch.unibe.scg.doodle.server;

import java.rmi.AlreadyBoundException;
import java.rmi.ConnectException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

import ch.unibe.scg.doodle.simon.SimonServer;

/**
 * The activator class controls the plug-in life cycle
 */
public class Activator extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "aPlugin"; //$NON-NLS-1$

	// The shared instance
	private static Activator plugin;

	/**
	 * The constructor
	 */
	public Activator() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext
	 * )
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
		
		// use SIMON instead of RMI
		int port = 58800;
		try {
			System.out.println("Starting SIMON server at port "+port);
			new SimonServer(port);
			System.out.println("Server started successfully.");
		} catch (Exception e) {
			System.out.println("Server could not be started.");
			e.printStackTrace();
		}
		
//		System.out.println("Trying to start server...");
//		this.startServer();
//		System.out.println("Server started");
	}

	void startServer() throws RemoteException {
		if (System.getSecurityManager() == null) {
			System.setSecurityManager(new DoodleSecurityManager());
		}

		String name = "DoodleDebug";
		PluginServer engine = new RealPluginServer();
		PluginServer stub = (PluginServer) UnicastRemoteObject.exportObject(
				engine, 1098);
		Registry registry = LocateRegistry.getRegistry();
		try {
			registry.rebind(name, stub); // XXX: Error occurs here!
		} catch (ConnectException e) {
			System.out.println("java.rmi.ConnectException occured!");
		}
//		try {
//			registry.bind(name, stub);
//		} catch (AlreadyBoundException e) {
//			registry.rebind(name, stub);
//		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext
	 * )
	 */
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 * 
	 * @return the shared instance
	 */
	public static Activator getDefault() {
		return plugin;
	}

	/**
	 * Returns an image descriptor for the image file at the given plug-in
	 * relative path
	 * 
	 * @param path
	 *            the path
	 * @return the image descriptor
	 */
	public static ImageDescriptor getImageDescriptor(String path) {
		return imageDescriptorFromPlugin(PLUGIN_ID, path);
	}
}
