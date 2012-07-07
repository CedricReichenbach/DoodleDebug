package ch.unibe.scg.doodle.server.views;

import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

public class HtmlShow implements Runnable {

	private String html;

	public HtmlShow(String html) {
		this.html = html;
	}

	@Override
	public void run() {
		IViewPart view = findView();
		assert (view != null);
		((DoodleDebugView) view).showHtml(html);
	}

	protected IViewPart findView() {
		IViewPart view = null;
		IWorkbenchWindow[] windows = PlatformUI.getWorkbench()
				.getWorkbenchWindows();
		for (IWorkbenchWindow window : windows) {
			IWorkbenchPage[] pages = window.getPages();
			for (IWorkbenchPage page : pages) {
				try {
					page.showView(DoodleDebugView.ID);
				} catch (PartInitException e) {
					throw new RuntimeException(e);
				}
				view = page.findView(DoodleDebugView.ID);
				if (view != null) {
					return view;
				}
			}
		}
		System.err.println("Warning: DoodleDebug view not opened");
		return null;
	}
}
