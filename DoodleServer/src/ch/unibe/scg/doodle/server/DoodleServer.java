package ch.unibe.scg.doodle.server;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Enumeration;
import java.util.HashMap;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;

import ch.unibe.scg.doodle.IndexedObjectStorage;
import ch.unibe.scg.doodle.OutputManager;
import ch.unibe.scg.doodle.htmlgen.Tag;
import ch.unibe.scg.doodle.rendering.RenderingRegistry;
import ch.unibe.scg.doodle.server.views.DoodleDebugView;
import ch.unibe.scg.doodle.server.views.JavascriptExecuter;
import ch.unibe.scg.doodle.util.JavascriptCallsUtil;
import ch.unibe.scg.doodle.util.PluginUtil;
import ch.unibe.scg.doodle.view.CSSCollection;

public class DoodleServer {
	private static final DoodleServer instance = new DoodleServer();
	private URLClassLoader clientClassLoader;

	private final IndexedObjectStorage storage = new IndexedObjectStorage();
	private LightboxStack stack;

	public static DoodleServer instance() {
		return instance;
	}

	protected DoodleServer() {
	}

	public void drawObjectWithID(int id) {
		Object o = this.storage.get(id);
		if (o == null) {
			// TODO: Show message that object is not available anymore
			return;
		}

		drawIntoLightbox(o);
	}

	private void drawIntoLightbox(Object o) {
		if (stack == null) {
			stack = new LightboxStack(o);
		} else {
			stack.push(o);
		}

		drawStack();
	}

	private void drawStack() {
		Tag lightboxContentWrapper = new Tag("div", "id=lightboxContentWrapper");
		OutputManager.instance().renderIntoLightbox(stack,
				lightboxContentWrapper);
		String toRender = lightboxContentWrapper.toString();
		Runnable javascriptExecuter = new JavascriptExecuter(
				JavascriptCallsUtil.showInLightboxCall(toRender));
		Display.getDefault().syncExec(javascriptExecuter);
	}

	public void clearOutput() {
		OutputManager.instance().initOutput();

		// XXX: Why? Can we delete this?
		// Runnable emptyShow = new HtmlShow(new
		// DoodleDebugScreen().toString());
		// Display.getDefault().asyncExec(emptyShow);
	}

	public void firstRun() {
		this.clearOutput();
		this.resetFocusTimer();
		this.lightboxClosed();
		CSSCollection.reset();
		this.clientClassLoader = DoodleClientWorkspace.getClientClassLoader();
		RenderingRegistry.clearUserPlugins();
	}

	private void resetFocusTimer() {
		DoodleDebugView view = (DoodleDebugView) PluginUtil
				.findDoodleDebugView();
		view.resetFocusTimer();
	}

	public void lightboxClosed() {
		this.stack = null;
	}

	public void cutoffFromStack(int num) {
		stack.cutOffTop(num);
		drawStack();
	}

	public void openJavaFile(String className, int lineNumber) {
		URL url = getJavaFileURL(className);
		if (url == null)
			return;

		openFileAtLine(url, lineNumber);
	}

	public URL getJavaFileURL(String className) {
		// TODO: find right class instead of brute-force classloader method
		// Idea: IJavaProject.findType()

		String[] parts = className.split("\\.");
		String onlyClass = parts[parts.length - 1];
		Enumeration<URL> urls;
		try {
			urls = clientClassLoader.findResources(onlyClass + ".java");
		} catch (IOException e) {
			return null; // XXX: fail message or similar
		}
		while (urls.hasMoreElements()) {
			URL url = urls.nextElement();
			String urlString = url.toExternalForm();
			boolean match = true;
			for (String part : parts) {
				if (!urlString.contains(part))
					match = false;
			}
			if (match) {
				return url;
			}
			;
		}
		return null;
	}

	private void openFileAtLine(URL url, int lineNumber) {
		IWorkbenchPage page = activeWorkbenchPage();
		if (page == null) {
			System.err
					.println("WARNING: activeWorkbenchPage is null (DoodleServer)");
			return; // XXX: maybe message to user here
		}

		IFile file;
		try {
			IFile[] files = ResourcesPlugin.getWorkspace().getRoot()
					.findFilesForLocationURI(url.toURI());
			file = files[0];
		} catch (URISyntaxException e) {
			throw new RuntimeException(e);
		}
		IMarker marker;
		try {
			marker = file.createMarker(IMarker.TEXT);
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put(IMarker.LINE_NUMBER, lineNumber);
			marker.setAttributes(map);

			IDE.openEditor(page, marker);
			marker.delete();
		} catch (CoreException e) {
			throw new RuntimeException(e);
		}
	}

	private IWorkbenchPage activeWorkbenchPage() {
		IWorkbench wb = PlatformUI.getWorkbench();
		IWorkbenchWindow win = wb.getActiveWorkbenchWindow();
		IWorkbenchPage page = win.getActivePage();
		return page;
	}

	public int store(Object object) {
		return storage.store(object);
	}
}
