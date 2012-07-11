package ch.unibe.scg.doodle.server.views;

import java.awt.Desktop;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;

public class HtmlObserver {

	private final File FILE;
	/**
	 * Enables/disables storing of html code into file for introspection.
	 */
	private boolean developMode = true;
	/**
	 * Only effective if developMode is on.
	 */
	private boolean debugMode = false;

	public HtmlObserver() {
		// Win7: C:\Users\<user>\AppData\Local\Temp\doodledebug\output.html
		FILE = new File(System.getProperty("java.io.tmpdir")
				+ "/doodledebug/output.html");
	}

	public void htmlChangedTo(String text) {
		// for testing
		if (developMode) {
			storeToFile(FILE, text);
			if (debugMode)
				openInBrowser(FILE);
		}
	}

	private void storeToFile(File file, String html) {
		try {
			new File(file.getParent()).mkdirs();
			FileWriter fw = new FileWriter(file.getPath());
			BufferedWriter buf = new BufferedWriter(fw);
			buf.write(html);
			buf.close();
		} catch (IOException e) {
			System.err.println("SERVER: Could not write into file " + file
					+ "\n" + e.getMessage());
		}
	}

	private void openInBrowser(File file) {
		URI uri = file.toURI();
		try {
			Desktop.getDesktop().browse(uri);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
