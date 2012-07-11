package ch.unibe.scg.doodle.server.util;

import java.io.File;
import java.io.IOException;

import org.eclipse.swt.graphics.Image;

public class DoodleImages {
	public static Image getDoodleDebugIcon() {
		Image image;
		try {
			image = new Image(null, DoodleFiles
					.getFilePath("icons/doodledebug-icon.png").toFile().toURI()
					.toURL().openStream());
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}
		return image;
	}

	public static String getCloseWindowImageFilePath() {
		return DoodleFiles.getFilePath("img/close-button_16.png").toFile().toURI()
				.toString();
	}

	public static String getDoodleTextureImageFilePath() {
		return DoodleFiles.getFilePath("img/dd-tex.png").toFile().toURI()
				.toString();
	}
}
