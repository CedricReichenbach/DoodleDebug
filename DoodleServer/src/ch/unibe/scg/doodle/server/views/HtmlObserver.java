package ch.unibe.scg.doodle.server.views;

import java.awt.Desktop;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;

import ch.unibe.scg.doodle.properties.DoodleDebugProperties;

public class HtmlObserver {

	private final File FILE;

	public HtmlObserver() {
		FILE = DoodleDebugProperties.tempFileForOutput();
	}

	public void htmlChangedTo(String text) {
		// for testing
		if (DoodleDebugProperties.developMode()) {
			storeToFile(FILE, text);
			if (DoodleDebugProperties.openInBrowser())
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
