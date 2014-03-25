package ch.unibe.scg.doodle.server.views;

import org.eclipse.ui.IViewPart;

import ch.unibe.scg.doodle.util.PluginUtil;

public class JavascriptExecuter extends HtmlShow {

	private String script;

	public JavascriptExecuter(String javascript) {
		super(javascript); // XXX nonsense...
		this.script = javascript;
	}

	@Override
	public void run() {
		IViewPart view = PluginUtil.findDoodleDebugView(); // XXX: sets focus
		assert (view != null);
		((DoodleDebugView) view).runJavascript(script);
	}

}
