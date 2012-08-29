package ch.unibe.scg.doodle.util;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class SystemUtil {
	public static void openInBrowser(URL url) {
		String urlString = url.toExternalForm().replace(" ", "%20");
		try {
			Desktop.getDesktop().browse(new URI(urlString));
		} catch (IOException | URISyntaxException e) {
			throw new RuntimeException(e);
		}
	}
}
