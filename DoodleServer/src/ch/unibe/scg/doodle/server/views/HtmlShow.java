package ch.unibe.scg.doodle.server.views;

import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IViewPart;

import ch.unibe.scg.doodle.util.PluginUtil;
import ch.unibe.scg.doodle.view.HtmlDocument;

public class HtmlShow implements Runnable {

	private String html;

	public HtmlShow(String html) {
		this.html = html;
	}

	public HtmlShow(HtmlDocument htmlDocument) {
		this(htmlDocument.toString());
	}

	@Override
	public void run() {
		IViewPart view = PluginUtil.findDoodleDebugView();
		assert (view != null);
		((DoodleDebugView) view).showHtml(html);
	}
	
	public void asyncExec() {
		Display.getDefault().asyncExec(this);
	}

}
