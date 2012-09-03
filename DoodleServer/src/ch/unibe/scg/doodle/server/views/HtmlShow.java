package ch.unibe.scg.doodle.server.views;

import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

import ch.unibe.scg.doodle.util.PluginUtil;

public class HtmlShow implements Runnable {

	private String html;

	public HtmlShow(String html) {
		this.html = html;
	}

	@Override
	public void run() {
		IViewPart view = PluginUtil.findDoodleDebugView();
		assert (view != null);
		((DoodleDebugView) view).showHtml(html);
	}

}
