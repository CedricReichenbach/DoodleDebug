package ch.unibe.scg.doodle.server.quickfix;

import java.util.ArrayList;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.ui.text.java.ClasspathFixProcessor.ClasspathFixProposal;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.NullChange;
import org.eclipse.swt.graphics.Image;

import ch.unibe.scg.doodle.server.buildpath.DoodleDebugContainer;
import ch.unibe.scg.doodle.server.buildpath.DoodleDebugContainerPage;
import ch.unibe.scg.doodle.server.buildpath.DoodleDebugContainterInitializer;
import ch.unibe.scg.doodle.server.util.DoodleImages;

public class DoodleClasspathFixProposal extends ClasspathFixProposal {

	private final IJavaProject fProject;
	private final int fRelevance;

	public DoodleClasspathFixProposal(IJavaProject project, int relevance) {
		this.fProject = project;
		this.fRelevance = relevance;
	}

	@Override
	public Change createChange(IProgressMonitor monitor) throws CoreException {
		if (monitor == null) {
			monitor = new NullProgressMonitor();
		}
		monitor.beginTask("Adding DoodleDebug library...", 1);
		try {
			IClasspathEntry entry = null;
			entry = JavaCore.newContainerEntry(new Path(
					DoodleDebugContainterInitializer.DD_CONTAINER_ID));
			IClasspathEntry[] oldEntries = fProject.getRawClasspath();
			ArrayList<IClasspathEntry> newEntries = new ArrayList<IClasspathEntry>(
					oldEntries.length + 1);
			boolean added = false;
			for (int i = 0; i < oldEntries.length; i++) {
				IClasspathEntry curr = oldEntries[i];
				if (curr.getEntryKind() == IClasspathEntry.CPE_CONTAINER) {
					IPath path = curr.getPath();
					if (path.equals(entry.getPath())) {
						return new NullChange(); // already on build path
					} else if (path.matchingFirstSegments(entry.getPath()) > 0) {
						if (!added) {
							curr = entry; // replace
							added = true;
						} else {
							curr = null;
						}
					}
				} else if (curr.getEntryKind() == IClasspathEntry.CPE_VARIABLE) {
					// IPath path= curr.getPath();
					// if (path.segmentCount() > 0 &&
					// JUnitCorePlugin.JUNIT_HOME.equals(path.segment(0))) {
					// if (!added) {
					// curr= entry; // replace
					// added= true;
					// } else {
					// curr= null;
					// }
					// }
				}
				if (curr != null) {
					newEntries.add(curr);
				}
			}
			if (!added) {
				newEntries.add(entry);
			}

			final IClasspathEntry[] newCPEntries = newEntries
					.toArray(new IClasspathEntry[newEntries.size()]);
			Change newClasspathChange = newClasspathChange(fProject,
					newCPEntries, fProject.getOutputLocation());
			if (newClasspathChange != null) {
				return newClasspathChange;
			}
		} finally {
			monitor.done();
		}
		return new NullChange();
	}

	@Override
	public String getDisplayString() {
		return "Add DoodleDebug library to build path";
	}

	@Override
	public String getAdditionalProposalInfo() {
		return "Adds the DoodleDebug library to the project's build path, which is necessary for the DoodleDebug plugin to work.";
	}

	@Override
	public Image getImage() {
		return DoodleImages.getDoodleDebugIcon();
	}

	@Override
	public int getRelevance() {
		return fRelevance;
	}

}
