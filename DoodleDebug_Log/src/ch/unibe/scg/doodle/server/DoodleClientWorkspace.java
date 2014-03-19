package ch.unibe.scg.doodle.server;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;

public class DoodleClientWorkspace {

	public static URLClassLoader getClientClassLoader() {
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		IProject[] projects = workspace.getRoot().getProjects();
		List<URL> urls = new ArrayList<URL>();
		for (IProject project : projects) {
			urls.addAll(getSubUrls(project.getLocation().toFile()));
		}
		URL[] urlArr = new URL[0];
		URLClassLoader clientWorkspaceClassLoader = new URLClassLoader(
				urls.toArray(urlArr),
				DoodleClientWorkspace.class.getClassLoader());
		return clientWorkspaceClassLoader;
	}

	private static List<URL> getSubUrls(File path) {
		List<URL> urls = new ArrayList<URL>();
		if (path == null)
			return urls;
		File[] subdirs = path.listFiles();
		if (subdirs == null)
			return urls; // XXX: Why?
		for (File dir : subdirs) {
			try {
				urls.add(dir.toURI().toURL());
			} catch (MalformedURLException e) {
				throw new RuntimeException(e);
			}
			urls.addAll(getSubUrls(dir));
		}
		return urls;
	}

}
