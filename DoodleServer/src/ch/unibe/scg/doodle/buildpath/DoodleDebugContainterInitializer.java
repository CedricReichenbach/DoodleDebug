package ch.unibe.scg.doodle.buildpath;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;

import org.eclipse.core.runtime.IPath;
import org.eclipse.jdt.core.ClasspathContainerInitializer;
import org.eclipse.jdt.core.IClasspathContainer;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;

public class DoodleDebugContainterInitializer extends
		ClasspathContainerInitializer {
	
	public static final String DD_CONTAINER_ID = "ch.unibe.scg.doodle.buildpath.DD_CONTAINER";

	private static final IPath DD_PATH = new Path(DD_CONTAINER_ID).append(""); // XXX

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
		IClasspathEntry entry = JavaCore.newContainerEntry(DD_PATH);
		return new DoodleDebugContainer(containerPath,
				new IClasspathEntry[] { entry });
	}

}
