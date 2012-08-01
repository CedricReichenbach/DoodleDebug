package ch.unibe.scg.doodle.view.js;

import java.io.IOException;
import java.net.URL;

import org.eclipse.core.runtime.FileLocator;

public class JSUtil {
	public static URL getJSURLFromFile(String filename) {
		try {
			return FileLocator.resolve(JSUtil.class.getResource(filename));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
