package ch.unibe.scg.doodle.server;

import ch.unibe.scg.doodle.OutputManager;
import ch.unibe.scg.doodle.database.IndexedObjectStorage;
import ch.unibe.scg.doodle.htmlgen.Tag;
import ch.unibe.scg.doodle.rendering.RenderingRegistry;
import ch.unibe.scg.doodle.util.JavascriptCallsUtil;
import ch.unibe.scg.doodle.view.CSSCollection;

public class DoodleServer {
	private static final DoodleServer instance = new DoodleServer();

	private final IndexedObjectStorage storage = new IndexedObjectStorage();
	private LightboxStack stack;

	public static DoodleServer instance() {
		return instance;
	}

	protected DoodleServer() {
	}

	public String drawObjectWithID(int id) {
		Object o = storage.get(id);
		if (o == null) {
			// TODO: Show message that object is not available anymore
			return "";
		}

		return drawIntoLightbox(o);
	}

	private String drawIntoLightbox(Object o) {
		if (stack == null) {
			stack = new LightboxStack(o);
		} else {
			stack.push(o);
		}

		return drawStack();
	}

	private String drawStack() {
		Tag lightboxContentWrapper = new Tag("div", "id=lightboxContentWrapper");
		OutputManager.instance().renderIntoLightbox(stack,
				lightboxContentWrapper);
		String toRender = lightboxContentWrapper.toString();
		return JavascriptCallsUtil.showInLightboxCall(toRender);
	}

	public void clearOutput() {
		OutputManager.instance().initOutput();
	}

	public void firstRun() {
		this.clearOutput();
		this.resetFocusTimer();
		this.lightboxClosed();
		CSSCollection.reset();
		RenderingRegistry.clearUserPlugins();
	}

	private void resetFocusTimer() {
		// TODO: Check possibilities for desktop notifications from webapp
	}

	public void lightboxClosed() {
		this.stack = null;
	}

	public String cutoffFromStack(int num) {
		stack.cutOffTop(num);
		return drawStack();
	}

	public int store(Object object) {
		return storage.store(object);
	}
}
