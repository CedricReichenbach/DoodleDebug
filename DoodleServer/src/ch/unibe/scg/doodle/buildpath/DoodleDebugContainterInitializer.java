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
		Path path = new Path(getClientJarPath());
		Path srcPath = null;
		IClasspathEntry entry = JavaCore.newLibraryEntry(path, srcPath, new Path("/"));
		//IClasspathEntry entry = JavaCore.newContainerEntry(DD_PATH);
		return new DoodleDebugContainer(containerPath,
				new IClasspathEntry[] { entry });
	}

	private String getClientJarPath() { // TODO: use DoodleFiles util
		Bundle bundle = Platform.getBundle("DoodleServer");
		Path path = new Path("DoodleDebug-Client.jar");
		URL fileURL = FileLocator.find(bundle, path, null);
		String result = "";
		try {
			result = FileLocator.resolve(fileURL).toString();
			
			// remove "file:/" XXX
			result = result.substring("file:/".length());
		} catch (IOException e) {
//			throw new RuntimeException(e);
		}
		return result;
	}

}
