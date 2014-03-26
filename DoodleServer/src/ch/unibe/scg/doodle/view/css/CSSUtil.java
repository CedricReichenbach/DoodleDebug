package ch.unibe.scg.doodle.view.css;

import java.net.URL;

public class CSSUtil {
	public static URL getCSSURLFromFile(String filename) {
		return CSSUtil.class.getResource(filename);
	}
}
