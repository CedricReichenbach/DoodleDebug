package ch.unibe.scg.doodle.util;

import org.eclipse.swt.graphics.Image;

public class DoodleImages {
	public static Image getDoodleDebugIcon() {
		Image image = new Image(null, DoodleFiles.getFilePath("icons/doodledebug-icon.png"));
		return image;
	}
}
