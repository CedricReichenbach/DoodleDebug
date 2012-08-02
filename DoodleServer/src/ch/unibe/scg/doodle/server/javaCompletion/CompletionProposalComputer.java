package ch.unibe.scg.doodle.server.javaCompletion;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jdt.core.CompletionContext;
import org.eclipse.jdt.ui.text.java.ContentAssistInvocationContext;
import org.eclipse.jdt.ui.text.java.IJavaCompletionProposalComputer;
import org.eclipse.jdt.ui.text.java.JavaContentAssistInvocationContext;
import org.eclipse.jface.text.contentassist.ICompletionProposal;
import org.eclipse.jface.text.contentassist.IContextInformation;

public class CompletionProposalComputer implements
		IJavaCompletionProposalComputer {

	private String shortcut = "doodle";

	@Override
	public void sessionStarted() {
	}

	@Override
	public List<ICompletionProposal> computeCompletionProposals(
			ContentAssistInvocationContext context, IProgressMonitor monitor) {
		List<ICompletionProposal> result = new ArrayList<ICompletionProposal>();
		if (context instanceof JavaContentAssistInvocationContext) {
			JavaContentAssistInvocationContext jcontext = (JavaContentAssistInvocationContext) context;
			CompletionContext corecontext = jcontext.getCoreContext();
			if (shortcut.startsWith(new String(corecontext.getToken()).toLowerCase())) {
				result.add(new DoodleCompletionProposal(shortcut, corecontext));
			}
		}
		return result;
	}

	@Override
	public List<IContextInformation> computeContextInformation(
			ContentAssistInvocationContext context, IProgressMonitor monitor) {
		List<IContextInformation> result = new ArrayList<IContextInformation>();
		return result;
	}

	@Override
	public String getErrorMessage() {
		return null;
	}

	@Override
	public void sessionEnded() {
	}

}
