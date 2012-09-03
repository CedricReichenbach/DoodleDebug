package ch.unibe.scg.doodle.server.views.actions;

import java.net.URL;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;

import ch.unibe.scg.doodle.server.util.DoodleFiles;
import ch.unibe.scg.doodle.server.util.DoodleImages;
import ch.unibe.scg.doodle.util.SystemUtil;

public class TutorialButtonAction extends Action implements IWorkbenchWindowActionDelegate {
	public TutorialButtonAction() {
		super("DoodleDebug Tutorial");
		this.setImageDescriptor(DoodleImages.getTutorialIcon());
		this.setDisabledImageDescriptor(DoodleImages.getTutorialIconDisabled());
	}

	@Override
	public void run() {
		URL url = DoodleFiles.getResolvedFileURL("tutorials/dd-tutorial.html");
		SystemUtil.openInBrowser(url);
	}

	@Override
	public void run(IAction action) {
		// for main toolbar actions
		this.run();
	}

	@Override
	public void selectionChanged(IAction action, ISelection selection) {
		
	}

	@Override
	public void dispose() {
	}

	@Override
	public void init(IWorkbenchWindow window) {
	}
}
