package ch.unibe.scg.doodle.view.fonts;

import java.net.URL;

public class FontUtil {
	public static URL getFontFile(String filename) {
		return FontUtil.class.getResource(filename);
	}
}
