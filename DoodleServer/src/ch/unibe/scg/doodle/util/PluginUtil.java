package ch.unibe.scg.doodle.util;

import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

import ch.unibe.scg.doodle.server.views.DoodleDebugView;

public class PluginUtil {
	public static IViewPart findDoodleDebugView() {
		IViewPart view = null;
		IWorkbenchWindow[] windows = PlatformUI.getWorkbench()
				.getWorkbenchWindows();
		for (IWorkbenchWindow window : windows) {
			IWorkbenchPage[] pages = window.getPages();
			for (IWorkbenchPage page : pages) {
				view = page.findView(DoodleDebugView.ID);
				if (view != null)
					return view;
				else {
					showDoodleDebugView(page);
					view = page.findView(DoodleDebugView.ID);
					if (view != null)
						return view;
				}
			}
		}
		System.err.println("Warning: DoodleDebug view not opened");
		return null;
	}

	private static void showDoodleDebugView(IWorkbenchPage page) {
		try {
			page.showView(DoodleDebugView.ID);
		} catch (PartInitException e) {
			throw new RuntimeException(e);
		}
	}

	public static void showDoodleDebugView() {
		IWorkbenchPage page = PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage();
		showDoodleDebugView(page);
	}
}
