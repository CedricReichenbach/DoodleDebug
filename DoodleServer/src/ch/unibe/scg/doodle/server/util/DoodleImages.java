package ch.unibe.scg.doodle.server.util;

import java.io.File;
import java.net.URL;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;

public class DoodleImages {
	public static Image getDoodleDebugIcon() {
		ImageDescriptor imageDescriptor = ImageDescriptor
				.createFromURL(getDoodleDebugIconURL());
		return imageDescriptor.createImage();
	}

	private static URL getDoodleDebugIconURL() {
		return DoodleFiles.getResolvedFileURL("/icons/doodledebug-icon.png");
	}

	public static String getDDIconPath() {
		return DoodleFiles.getFilePath("/icons/doodledebug-icon.png").toFile()
				.toURI().toString();
	}

	public static String getCloseWindowImageFilePath() {
		return DoodleFiles.getFilePath("/img/close-button_16.png").toFile()
				.toURI().toString();
	}

	public static String getDoodleTextureImageFilePath() {
		return DoodleFiles.getFilePath("/img/dd-tex.png").toFile().toURI()
				.toString();
	}

	public static File getLoadingErrorIcon() {
		return DoodleFiles.getFilePath("/img/loading-error.png").toFile();
	}

	/**
	 * Osterei
	 * 
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
