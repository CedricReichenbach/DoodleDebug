package ch.unibe.scg.doodle.server.quickfix;

import java.util.ArrayList;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.ui.text.java.ClasspathFixProcessor;
import org.eclipse.jdt.ui.text.java.ClasspathFixProcessor.ClasspathFixProposal;
import org.eclipse.jdt.ui.text.java.IInvocationContext;
import org.eclipse.jdt.ui.text.java.IJavaCompletionProposal;
import org.eclipse.jdt.ui.text.java.IProblemLocation;
import org.eclipse.jdt.ui.text.java.IQuickFixProcessor;
import org.eclipse.jdt.core.compiler.IProblem;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.rewrite.ImportRewrite;
import org.eclipse.jdt.ui.CodeStyleConfiguration;

public class DoodleQuickfixProcessor implements IQuickFixProcessor {

	@Override
	public boolean hasCorrections(ICompilationUnit unit, int problemId) {
		// return problemId == IProblem.UndefinedMethod
		// | problemId == IProblem.UndefinedType;
		return problemId == IProblem.UnresolvedVariable;
	}

	@Override
	public IJavaCompletionProposal[] getCorrections(IInvocationContext context,
			IProblemLocation[] locations) throws CoreException {
		for (IProblemLocation problem : locations) {
			if (problem.getProblemId() == 0x22000032
					&& problem.getProblemArguments()[0].equals("Doo"))
				// XXX filter relevant problems | I have no clue what this
				// problemID is right now
				// TODO: Check if build path already contains DoodleDebug library
				return getAddDDToBuildPathProposals(context);
		}
		return null;
	}

	private IJavaCompletionProposal[] getAddDDToBuildPathProposals(
			IInvocationContext context) {
		ICompilationUnit unit = context.getCompilationUnit();
		IJavaProject project = unit.getJavaProject();
		String name = "ch.unibe.scg.doodle.Doo";
		ClasspathFixProposal[] fixProposals = ClasspathFixProcessor
				.getContributedFixImportProposals(project, name, null);

		ArrayList<IJavaCompletionProposal> proposals = new ArrayList<IJavaCompletionProposal>();
		for (ClasspathFixProposal fixProposal : fixProposals) {
			proposals.add(new DoodleClasspathFixCorrelationProposal(project,
					fixProposal, getImportRewrite(context.getASTRoot(), name)));
		}

		IJavaCompletionProposal[] propArr = new IJavaCompletionProposal[proposals
				.size()];
		for (int i = 0; i < proposals.size(); i++) {
			propArr[i] = proposals.get(i);
		}
		return propArr;
	}

	private ImportRewrite getImportRewrite(CompilationUnit astRoot,
			String typeToImport) {
		if (typeToImport != null) {
			ImportRewrite importRewrite = CodeStyleConfiguration
					.createImportRewrite(astRoot, true);
			importRewrite.addImport(typeToImport);
			return importRewrite;
		}
		return null;
	}

}
