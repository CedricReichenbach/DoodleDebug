package ch.unibe.scg.doodle.util;

import java.awt.Desktop;
import java.net.URI;
import java.net.URL;

public class SystemUtil {
	public static void openInBrowser(URL url) {
		String urlString = url.toExternalForm().replace(" ", "%20");
		try {
			Desktop.getDesktop().browse(new URI(urlString));
//		} catch (IOException | URISyntaxException e) { // TODO when going to JRE 1.7: Multicatch
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
