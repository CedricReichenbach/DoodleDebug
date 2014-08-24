package ch.unibe.scg.doodle.view.js;

import java.net.URL;

public class JSUtil {
	public static URL getJSURLFromFile(String filename) {
		// TODO: Test this
		return JSUtil.class.getResource(filename);
	}
}
