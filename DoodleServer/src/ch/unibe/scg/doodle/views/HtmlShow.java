package ch.unibe.scg.doodle.views;

import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

import ch.unibe.scg.doodle.IndexedObjectStorage;

public class HtmlShow implements Runnable {

	private String html;
	private IndexedObjectStorage storage;

	public HtmlShow(String html, IndexedObjectStorage storage) {
		this.html = html;
		this.storage = storage;
	}

	@Override
	public void run() {
		IViewPart view = findView();
		assert (view != null);
		((DoodleDebugView) view).showHtml(html, storage);
	}

	private IViewPart findView() {
		IViewPart view = null;
		IWorkbenchWindow[] windows = PlatformUI.getWorkbench()
				.getWorkbenchWindows();
		for (IWorkbenchWindow window : windows) {
			IWorkbenchPage[] pages = window.getPages();
			for (IWorkbenchPage page : pages) {
				try {
					page.showView(DoodleDebugView.ID);
				} catch (PartInitException e) {
					System.out.println("HtmlShow: PartInitException");
				}
				view = page.findView(DoodleDebugView.ID);
				if (view != null) {
					return view;
				}
			}
		}
		System.out.println("Warning: DoodleDebug view not opened");
		return null;
	}
}
