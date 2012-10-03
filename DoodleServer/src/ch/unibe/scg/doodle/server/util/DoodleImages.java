package ch.unibe.scg.doodle.server.util;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;

public class DoodleImages {
	public static Image getDoodleDebugIcon() {
		ImageDescriptor imageDescriptor = ImageDescriptor
				.createFromURL(DoodleFiles
						.getResolvedFileURL("/icons/doodledebug-icon.png"));
		return imageDescriptor.createImage();
	}

	public static String getCloseWindowImageFilePath() {
		return DoodleFiles.getFilePath("/img/close-button_16.png").toFile()
				.toURI().toString();
	}

	public static String getDoodleTextureImageFilePath() {
		return DoodleFiles.getFilePath("/img/dd-tex.png").toFile().toURI()
				.toString();
	}

	/**
	 * Osterei
	 * @return
	 */
	public static String getOeImageFilePath() {
		return DoodleFiles.getFilePath("/img/oe-tile.gif").toFile().toURI()
				.toString();
	}

	public static ImageDescriptor getTutorialIcon() {
		return ImageDescriptor.createFromURL(DoodleFiles
				.getResolvedFileURL("/icons/tutorial-icon.png"));
	}

	public static ImageDescriptor getTutorialIconDisabled() {
		return ImageDescriptor.createFromURL(DoodleFiles
				.getResolvedFileURL("/icons/tutorial-icon_disabled.png"));
	}
}
