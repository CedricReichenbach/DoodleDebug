package ch.unibe.scg.doodle.view.fonts;

import java.net.URL;

public class FontUtil {
	public static URL getFontFileURL(String filename) {
		// XXX: Why do we even need this? -> See Boolean plugin
		throw new RuntimeException("Not implemented.");
		// try {
		// return FileLocator.resolve(FontUtil.class.getResource(filename));
		// } catch (IOException e) {
		// throw new RuntimeException(e);
		// }
	}
}
