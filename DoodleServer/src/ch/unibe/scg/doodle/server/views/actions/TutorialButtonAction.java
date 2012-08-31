package ch.unibe.scg.doodle.server.views.actions;

import java.net.URL;

import org.eclipse.jface.action.Action;

import ch.unibe.scg.doodle.server.util.DoodleFiles;
import ch.unibe.scg.doodle.server.util.DoodleImages;
import ch.unibe.scg.doodle.util.SystemUtil;

public class TutorialButtonAction extends Action {
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
}
