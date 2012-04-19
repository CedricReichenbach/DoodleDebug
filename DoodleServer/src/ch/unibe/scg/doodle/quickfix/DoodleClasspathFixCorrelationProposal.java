package ch.unibe.scg.doodle.quickfix;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.dom.rewrite.ImportRewrite;
import org.eclipse.jdt.ui.text.java.ClasspathFixProcessor.ClasspathFixProposal;
import org.eclipse.jdt.ui.text.java.IJavaCompletionProposal;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.contentassist.IContextInformation;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.CompositeChange;
import org.eclipse.ltk.core.refactoring.PerformChangeOperation;
import org.eclipse.ltk.core.refactoring.RefactoringCore;
import org.eclipse.ltk.core.refactoring.TextFileChange;

public class DoodleClasspathFixCorrelationProposal implements
		IJavaCompletionProposal {

	private final IJavaProject fJavaProject;
	private final ClasspathFixProposal fClasspathFixProposal;
	private final ImportRewrite fImportRewrite;

	public DoodleClasspathFixCorrelationProposal(IJavaProject project,
			ClasspathFixProposal classpathFixProposal,
			ImportRewrite importRewrite) {
		fJavaProject = project;
		fClasspathFixProposal = classpathFixProposal;
		fImportRewrite = importRewrite;
	}

	@Override
	public void apply(IDocument document) {
		try {
			PlatformUI.getWorkbench().getActiveWorkbenchWindow()
					.run(false, true, new IRunnableWithProgress() {
						public void run(IProgressMonitor monitor)
								throws InvocationTargetException,
								InterruptedException {
							try {
								Change change = createChange();
								change.initializeValidationData(new NullProgressMonitor());
								PerformChangeOperation op = new PerformChangeOperation(
										change);
								op.setUndoManager(
										RefactoringCore.getUndoManager(),
										getDisplayString());
								op.setSchedulingRule(fJavaProject.getProject()
										.getWorkspace().getRoot());
								op.run(monitor);
							} catch (CoreException e) {
								throw new InvocationTargetException(e);
							} catch (OperationCanceledException e) {
								throw new InterruptedException();
							}
						}
					});
		} catch (InvocationTargetException e) {
			// fail silently
		} catch (InterruptedException e) {
			// fail silently
		}
	}

	protected Change createChange() throws CoreException {
		Change change = fClasspathFixProposal.createChange(null);
		if (fImportRewrite != null) {
			TextFileChange cuChange = new TextFileChange(
					"Add import", (IFile) fImportRewrite.getCompilationUnit().getResource()); //$NON-NLS-1$
			cuChange.setEdit(fImportRewrite.rewriteImports(null));

			CompositeChange composite = new CompositeChange(getDisplayString());
			composite.add(change);
			composite.add(cuChange);
			return composite;
		}
		return change;
	}

	@Override
	public Point getSelection(IDocument document) {
		return null;
	}

	@Override
	public String getAdditionalProposalInfo() {
		return fClasspathFixProposal.getAdditionalProposalInfo();
	}

	@Override
	public String getDisplayString() {
		return fClasspathFixProposal.getDisplayString();
	}

	@Override
	public Image getImage() {
		return fClasspathFixProposal.getImage();
	}

	@Override
	public IContextInformation getContextInformation() {
		return null;
	}

	@Override
	public int getRelevance() {
		return fClasspathFixProposal.getRelevance();
	}

}
