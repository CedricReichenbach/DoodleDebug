package ch.unibe.scg.doodle.buildpath;

import org.eclipse.core.runtime.IPath;
import org.eclipse.jdt.core.IClasspathContainer;
import org.eclipse.jdt.core.IClasspathEntry;

public class DoodleDebugContainer implements IClasspathContainer {

	private final IClasspathEntry[] fEntries;
	private final IPath fPath;

	public DoodleDebugContainer(IPath path, IClasspathEntry[] entries) {
		fPath= path;
		fEntries= entries;
	}
	
	@Override
	public IClasspathEntry[] getClasspathEntries() {
		return fEntries;
	}

	@Override
	public String getDescription() {
		return "Container for DoodleDebug (client side)";
	}

	@Override
	public int getKind() {
		return IClasspathContainer.K_APPLICATION;
	}

	@Override
	public IPath getPath() {
		return fPath;
	}

}
