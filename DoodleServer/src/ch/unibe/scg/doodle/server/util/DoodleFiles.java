package ch.unibe.scg.doodle.server.util;

//import static org.hamcrest.CoreMatchers.*;
//import static org.junit.Assert.*;

import java.io.IOException;
import java.net.URL;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
//import org.junit.Test;
import org.osgi.framework.Bundle;

public class DoodleFiles {
	public static IPath getFilePath(String file) {
		URL fileURL = getResolvedFileURL(file);
		String result = "";
		result = fileURL.getPath();
		return Path.fromOSString(result);
	}

	public static URL getResolvedFileURL(String file) {
		Bundle bundle = Platform.getBundle("DoodleServer");
		Path path = new Path(file);
		URL fileURL = FileLocator.find(bundle, path, null);
		try {
			return FileLocator.resolve(fileURL);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
