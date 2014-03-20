package ch.unibe.scg.doodle.server;

import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

public class DoodleClientClassManager {

	public static URLClassLoader getClientClassLoader() {
		List<URL> urls = new ArrayList<URL>();
		URL[] urlArr = new URL[0];
		// TODO: Transport class files over network, store them and create classloader
		URLClassLoader clientWorkspaceClassLoader = new URLClassLoader(
				urls.toArray(urlArr),
				DoodleClientClassManager.class.getClassLoader());
		return clientWorkspaceClassLoader;
	}


}
