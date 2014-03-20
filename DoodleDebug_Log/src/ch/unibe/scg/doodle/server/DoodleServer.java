package ch.unibe.scg.doodle.server;

import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Enumeration;

import ch.unibe.scg.doodle.IndexedObjectStorage;

public class DoodleServer {
	private static final DoodleServer instance = new DoodleServer();
	private URLClassLoader clientClassLoader;

	private final IndexedObjectStorage storage = new IndexedObjectStorage();

	public static DoodleServer instance() {
		return instance;
	}

	protected DoodleServer() {
	}

	public URL getJavaFileURL(String className) {
		// TODO: find right class instead of brute-force classloader method
		// Idea: IJavaProject.findType()

		String[] parts = className.split("\\.");
		String onlyClass = parts[parts.length - 1];
		Enumeration<URL> urls;
		try {
			urls = clientClassLoader.findResources(onlyClass + ".java");
		} catch (IOException e) {
			return null; // XXX: fail message or similar
		}
		while (urls.hasMoreElements()) {
			URL url = urls.nextElement();
			String urlString = url.toExternalForm();
			boolean match = true;
			for (String part : parts) {
				if (!urlString.contains(part))
					match = false;
			}
			if (match) {
				return url;
			}
			;
		}
		return null;
	}

	public int store(Object object) {
		return storage.store(object);
	}
}
