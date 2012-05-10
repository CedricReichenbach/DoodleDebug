package ch.unibe.scg.doodle.view.js;

import ch.unibe.scg.doodle.util.FileUtil;

public class JSUtil {
	public static String getJSFromFile(String filename) {
		return FileUtil.readFile(JSUtil.class.getResource(filename));
	}
}
