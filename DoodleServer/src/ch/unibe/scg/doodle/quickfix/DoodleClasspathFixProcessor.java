package ch.unibe.scg.doodle.quickfix;

import java.util.ArrayList;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.ui.text.java.ClasspathFixProcessor;

public class DoodleClasspathFixProcessor extends ClasspathFixProcessor {
	@Override
	public ClasspathFixProposal[] getFixImportProposals(IJavaProject project,
			String missingType) throws CoreException {
		ArrayList<DoodleClasspathFixProposal> proposals= new ArrayList<DoodleClasspathFixProposal>();
		proposals.add(new DoodleClasspathFixProposal(project, 15));
		return proposals.toArray(new ClasspathFixProposal[proposals.size()]);
	}

}
