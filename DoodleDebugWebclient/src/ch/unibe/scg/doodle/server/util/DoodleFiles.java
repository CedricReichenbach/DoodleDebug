package ch.unibe.scg.doodle.server.util;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

//import org.junit.Test;

public class DoodleFiles {

	public static final String PLUGIN_ID = "ch.unibe.scg.doodledebug";

	public static URI getFileURI(String file) {
		try {
			return getResolvedFileURL(file).toURI();
		} catch (URISyntaxException e) {
			throw new RuntimeException(e);
		}
	}

	public static URL getResolvedFileURL(String file) {
		// TODO
		try {
			return new URL("http://www.notimplemented.org/");
		} catch (MalformedURLException e) {
			throw new RuntimeException(e);
		}
	}

}
