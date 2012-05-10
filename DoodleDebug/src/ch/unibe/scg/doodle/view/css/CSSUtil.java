package ch.unibe.scg.doodle.view.css;

import ch.unibe.scg.doodle.util.FileUtil;

public class CSSUtil {
	public static String getCSSFromFile(String filename) {
		return FileUtil.readFile(CSSUtil.class.getResource(filename));
	}
}
