package ch.unibe.scg.doodle.server;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;

public class DoodleClientWorkspace {

	public static URLClassLoader getClientClassLoader() {
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		IProject[] projects = workspace.getRoot().getProjects();
		List<URL> urls = new ArrayList<URL>();
		for (IProject project : projects) {
			// try {
			getSubUrls(project.getLocation().toFile());
			// urls.add(project.getLocationURI().resolve("bin/").toURL());
			// } catch (MalformedURLException e) {
			// throw new RuntimeException(e);
			// }
		}
		URL[] urlArr = new URL[0];
		URLClassLoader clientWorkspaceClassLoader = new URLClassLoader(
				urls.toArray(urlArr),
				DoodleClientWorkspace.class.getClassLoader());
		System.out.println(urls);
		return clientWorkspaceClassLoader;
	}

	private static List<URL> getSubUrls(File path) {
		List<URL> urls = new ArrayList<URL>();
		File[] subdirs = path.listFiles();
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
