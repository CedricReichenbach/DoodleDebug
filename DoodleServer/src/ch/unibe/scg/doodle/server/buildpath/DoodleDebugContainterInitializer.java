package ch.unibe.scg.doodle.server.buildpath;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jdt.core.ClasspathContainerInitializer;
import org.eclipse.jdt.core.IClasspathContainer;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;

import ch.unibe.scg.doodle.server.util.DoodleFiles;

public class DoodleDebugContainterInitializer extends
		ClasspathContainerInitializer {

	public static final String DD_CONTAINER_ID = "ch.unibe.scg.doodle.buildpath.DD_CONTAINER";

	// private static final IPath DD_PATH = new Path(DD_CONTAINER_ID);

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
		IClasspathEntry entry = JavaCore.newLibraryEntry(path, srcPath,
				new Path("/"));
		// IClasspathEntry entry = JavaCore.newContainerEntry(DD_PATH);
		return new DoodleDebugContainer(containerPath,
				new IClasspathEntry[] { entry });
	}

	private IPath getClientJarPath() {
		IPath path = DoodleFiles.getFilePath("/DoodleDebug-Client.jar");
		if (path.getDevice().contains("file:")) {// happens with update site
			return new Path(
					"C:\\Users\\Cedric Reichenbach\\.eclipse\\org.eclipse.platform_3.7.0_740800064\\plugins\\ch.unibe.scg.doodledebug_1.0.0.201208290028.jar!\\DoodleDebug-Client.jar");
			// path.setDevice(null);
		}
		return path;
	}
}
