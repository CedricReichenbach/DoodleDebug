package ch.unibe.scg.doodle.util;

import org.eclipse.swt.widgets.Display;

import ch.unibe.scg.doodle.server.views.JavascriptExecuter;

public class OutputUtil {
	public static void executeJSInOutput(String script) {
		Runnable jsExecuterForCSS = new JavascriptExecuter(script);
		Display.getDefault().syncExec(jsExecuterForCSS);
	}
}
