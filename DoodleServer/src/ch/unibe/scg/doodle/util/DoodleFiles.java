package ch.unibe.scg.doodle.util;

import java.io.IOException;
import java.net.URL;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.osgi.framework.Bundle;

public class DoodleFiles {
	public static String getFilePath(String file) {
			Bundle bundle = Platform.getBundle("DoodleServer");
			Path path = new Path(file);
			URL fileURL = FileLocator.find(bundle, path, null);
			String result = "";
			try {
				result = FileLocator.resolve(fileURL).toString();
				
				// remove "file:/" XXX
				result = result.substring("file:/".length());
			} catch (IOException e) {
//				throw new RuntimeException(e);
			}
			return result;
	}
}
