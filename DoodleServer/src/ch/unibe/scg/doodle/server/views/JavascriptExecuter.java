package ch.unibe.scg.doodle.server.views;

import org.eclipse.ui.IViewPart;

public class JavascriptExecuter extends HtmlShow {

	private String script;

	public JavascriptExecuter(String javascript) {
		super(javascript); // XXX nonsense...
		this.script = javascript;
	}

	@Override
	public void run() {
		IViewPart view = findView();
		assert (view != null);
		((DoodleDebugView) view).runJavascript(script);
	}

}
