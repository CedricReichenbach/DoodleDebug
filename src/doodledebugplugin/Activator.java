package doodledebugplugin;

import java.io.File;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * This class controls the plug-in life cycle.
 */
public class Activator extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "DoodleDebugPlugin"; //$NON-NLS-1$

	// The shared instance
	private static Activator plugin;
	

	public Activator() {
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
		
		File.createTempFile("C://Windows/temp/irgendwas", "txt");
		System.out.println("asdf");
		
		startServer();
	}

	void startServer() {
		if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }
        try {
            String name = "DoodleDebug";
            PluginServer engine = new RealPluginServer();
            PluginServer stub =
                (PluginServer) UnicastRemoteObject.exportObject(engine, 1098);
            Registry registry = LocateRegistry.getRegistry();
            registry.rebind(name, stub);
        } catch (RemoteException re) {
        	throw new RuntimeException(re);
        } 
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
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

}
