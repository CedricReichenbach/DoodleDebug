package ch.unibe.scg.doodle.view.css;

import java.io.IOException;
import java.net.URL;

import org.eclipse.core.runtime.FileLocator;

public class CSSUtil {
	public static URL getCSSURLFromFile(String filename) {
		try {
			return FileLocator.resolve(CSSUtil.class.getResource(filename));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
