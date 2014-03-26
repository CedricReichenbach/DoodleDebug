package ch.unibe.scg.doodle.server.util;

import java.io.IOException;
import java.net.URL;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
//import org.junit.Test;
import org.osgi.framework.Bundle;

public class DoodleFiles {

	public static final String PLUGIN_ID = "ch.unibe.scg.doodledebug";

	public static IPath getFilePath(String file) {
		URL fileURL = getResolvedFileURL(file);
		String result = fileURL.getPath();
		return Path.fromPortableString(result);
	}

	public static URL getResolvedFileURL(String file) {
		Bundle bundle = Platform.getBundle(PLUGIN_ID);
		Path path = new Path(file);
		URL fileURL = FileLocator.find(bundle, path, null);
		try {
			return FileLocator.resolve(fileURL);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
