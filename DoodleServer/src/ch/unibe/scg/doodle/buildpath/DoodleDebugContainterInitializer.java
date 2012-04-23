package ch.unibe.scg.doodle.buildpath;

import java.io.IOException;
import java.net.URL;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jdt.core.ClasspathContainerInitializer;
import org.eclipse.jdt.core.IClasspathContainer;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.osgi.framework.Bundle;

import ch.unibe.scg.doodle.util.DoodleFiles;

public class DoodleDebugContainterInitializer extends
		ClasspathContainerInitializer {
	
	public static final String DD_CONTAINER_ID = "ch.unibe.scg.doodle.buildpath.DD_CONTAINER";

//	private static final IPath DD_PATH = new Path(DD_CONTAINER_ID);

	public DoodleDebugContainterInitializer() {
	}

	@Override
	public void initialize(IPath containerPath, IJavaProject project)
			throws CoreException {
		DoodleDebugContainer container = getNewContainer(containerPath);
		JavaCore.setClasspathContainer(containerPath,
				new IJavaProject[] { project },
				new IClasspathContainer[] { container }, null);
	}

	private DoodleDebugContainer getNewContainer(IPath containerPath) {
		final IPath path = getClientJarPath();
		final IPath srcPath = null;
		IClasspathEntry entry = JavaCore.newLibraryEntry(path, srcPath, new Path("/"));
		//IClasspathEntry entry = JavaCore.newContainerEntry(DD_PATH);
		return new DoodleDebugContainer(containerPath,
				new IClasspathEntry[] { entry });
	}

	private IPath getClientJarPath() {
		return DoodleFiles.getFilePath("DoodleDebug-Client.jar");
	}

}
