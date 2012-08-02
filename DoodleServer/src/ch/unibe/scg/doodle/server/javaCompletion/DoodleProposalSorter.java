package ch.unibe.scg.doodle.server.javaCompletion;

import org.eclipse.jdt.ui.text.java.AbstractProposalSorter;
import org.eclipse.jface.text.contentassist.ICompletionProposal;

public class DoodleProposalSorter extends AbstractProposalSorter {

	public DoodleProposalSorter() {
		System.out.println("peng");
	}

	@Override
	public int compare(ICompletionProposal p1, ICompletionProposal p2) {
		System.out.println("peng");
		if (p1 instanceof DoodleCompletionProposal)
			return 1;
		if (p2 instanceof DoodleCompletionProposal)
			return -1;
		return 0;
	}

}
