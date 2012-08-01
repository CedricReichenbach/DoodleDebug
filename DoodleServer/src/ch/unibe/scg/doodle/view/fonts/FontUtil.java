package ch.unibe.scg.doodle.view.fonts;

import java.io.IOException;
import java.net.URL;

import org.eclipse.core.runtime.FileLocator;

public class FontUtil {
	public static URL getFontFileURL(String filename) {
		try {
			return FileLocator.resolve(FontUtil.class.getResource(filename));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
