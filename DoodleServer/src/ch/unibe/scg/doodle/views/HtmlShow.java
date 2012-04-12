package ch.unibe.scg.doodle.views;

import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

public class HtmlShow implements Runnable {
	
	private String html;
	
	public HtmlShow(String html) {
		this.html = html;
	}

	@Override
	public void run() {
		IViewPart view = null;
		IWorkbenchWindow[] windows = PlatformUI.getWorkbench().getWorkbenchWindows();
		for (IWorkbenchWindow window : windows) {
			IWorkbenchPage[] pages = window.getPages();
			for (IWorkbenchPage page : pages) {
				view = page.findView(DoodleDebugView.ID);
			}
		}
		assert(view != null);
		((DoodleDebugView) view).showHtml(html);
	}

}
