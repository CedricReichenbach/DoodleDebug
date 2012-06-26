package ch.unibe.scg.doodle.server;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;

import ch.unibe.scg.doodle.D;
import ch.unibe.scg.doodle.IndexedObjectStorage;

public class DoodleServer {
	private static DoodleServer instance;

	public static DoodleServer instance() {
		if (instance == null) {
			return new DoodleServer();
		}
		return instance;
	}

	protected DoodleServer() {

	}

	private IndexedObjectStorage storage;

	public void setStorage(IndexedObjectStorage storage) {
		this.storage = storage;
	}

	public void drawObjectWithID(int id) {
		if (this.storage == null) {
			D.raw("Sorry, don't know that object.");
			return;
		}
		Object o = this.storage.get(id);
		D.raw(o);
	}

	public ClassLoader getWorkspaceClassLoader() {
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		IProject[] projects = workspace.getRoot().getProjects();
		// TODO: extract build folder of running project
		return null;
	}

}
