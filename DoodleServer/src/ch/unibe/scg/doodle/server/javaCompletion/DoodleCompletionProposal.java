package ch.unibe.scg.doodle.server.javaCompletion;

import org.eclipse.jdt.core.CompletionContext;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.DocumentEvent;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.contentassist.ICompletionProposal;
import org.eclipse.jface.text.contentassist.ICompletionProposalExtension2;
import org.eclipse.jface.text.contentassist.IContextInformation;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;

import ch.unibe.scg.doodle.server.util.DoodleImages;

public class DoodleCompletionProposal implements ICompletionProposal,
		ICompletionProposalExtension2 {

	private CompletionContext context;
	private String shortcut;

	public DoodleCompletionProposal(String shortcut,
			CompletionContext corecontext) {
		this.shortcut = shortcut;
		this.context = corecontext;
	}

	@Override
	public void apply(IDocument document) {
		// TODO Auto-generated method stub

	}

	@Override
	public Point getSelection(IDocument document) {
		return new Point(context.getTokenStart() + "Doo.dle(".length(),
				"object".length());
	}

	@Override
	public String getAdditionalProposalInfo() {
		return "Doo.dle()";
	}

	@Override
	public String getDisplayString() {
		return shortcut;
	}

	@Override
	public Image getImage() {
		return DoodleImages.getDoodleDebugIcon();
	}

	@Override
	public IContextInformation getContextInformation() {
		return null;
	}

	@Override
	public void apply(ITextViewer viewer, char trigger, int stateMask,
			int offset) {
		IDocument doc = viewer.getDocument();
		int start = context.getTokenStart();
		try {
			doc.replace(start, offset - start, "Doo.dle(object);");
		} catch (BadLocationException e) {
			throw new RuntimeException(e); // XXX: too hard?
		}
	}

	@Override
	public void selected(ITextViewer viewer, boolean smartToggle) {
	}

	@Override
	public void unselected(ITextViewer viewer) {
	}

	@Override
	public boolean validate(IDocument document, int offset, DocumentEvent event) {
		try {
			int start = context.getTokenStart();
			int length = offset - start;
			String prefix = document.get(start, length);
			return !prefix.isEmpty() && (shortcut.startsWith(prefix));
		} catch (BadLocationException e) {
			throw new RuntimeException(e); // XXX: too hard?
		}
	}
}
