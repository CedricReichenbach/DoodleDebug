package ch.unibe.scg.doodle.util;

import java.io.IOException;

import org.eclipse.swt.graphics.Image;

public class DoodleImages {
	public static Image getDoodleDebugIcon() {
		Image image;
		try {
			image = new Image(null, DoodleFiles.getFilePath("icons/doodledebug-icon.png").toFile().toURI().toURL().openStream());
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}
		return image;
	}
}
