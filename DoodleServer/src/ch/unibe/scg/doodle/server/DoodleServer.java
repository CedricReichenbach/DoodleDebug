package ch.unibe.scg.doodle.server;

import org.eclipse.swt.widgets.Display;

import ch.unibe.scg.doodle.DMockup;
import ch.unibe.scg.doodle.Doodler;
import ch.unibe.scg.doodle.IndexedObjectStorage;
import ch.unibe.scg.doodle.htmlgen.Tag;
import ch.unibe.scg.doodle.server.views.HtmlShow;
import ch.unibe.scg.doodle.server.views.JavascriptExecuter;
import ch.unibe.scg.doodle.util.JavascriptCallsUtil;
import ch.unibe.scg.doodle.view.DoodleDebugScreen;
import ch.unibe.scg.doodle.view.HtmlDocument;

public class DoodleServer {
	private static DoodleServer instance;

	public static DoodleServer instance() {
		if (instance == null) {
			instance = new DoodleServer();
		}
		return instance;
	}

	protected DoodleServer() {

	}

	private IndexedObjectStorage storage;
	private LightboxStack stack;

	public void setStorage(IndexedObjectStorage storage) {
		this.storage = storage;
	}

	public void drawObjectWithID(int id) {
		if (this.storage == null) {
			DMockup.raw("Sorry, don't know that object.");
			return;
		}
		Object o = this.storage.get(id);
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
		Doodler.instance().renderIntoLightbox(stack, lightboxContentWrapper);
		String toRender = lightboxContentWrapper.toString();
		Runnable javascriptExecuter = new JavascriptExecuter(
				JavascriptCallsUtil.showInLightboxCall(toRender));
		Display.getDefault().syncExec(javascriptExecuter);
	}

	public void clearOutput() {
		DMockup.resetInstance();
		Runnable emptyShow = new HtmlShow(new DoodleDebugScreen().toString());
		Display.getDefault().asyncExec(emptyShow);
	}

	public void firstRun() {
		this.clearOutput();
		this.lightboxClosed();
	}

	public void lightboxClosed() {
		this.stack = null;
	}

	public void cutoffFromStack(int num) {
		stack.cutOffTop(num);
		drawStack();
	}
}
