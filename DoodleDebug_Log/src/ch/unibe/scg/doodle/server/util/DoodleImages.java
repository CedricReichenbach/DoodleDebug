package ch.unibe.scg.doodle.server.util;

import java.io.File;
import java.net.URL;

public class DoodleImages {

	public static URL getDoodleDebugIconURL() {
		return DoodleFiles.getResolvedFileURL("/icons/doodledebug-icon.png");
	}

	public static String getDDIconPath() {
		return DoodleFiles.getFilePath("/icons/doodledebug-icon.png").toURI()
				.toString();
	}

	public static String getCloseWindowImageFilePath() {
		return DoodleFiles.getFilePath("/img/close-button_16.png").toURI()
				.toString();
	}

	public static String getDoodleTextureImageFilePath() {
		return DoodleFiles.getFilePath("/img/dd-tex.png").toURI().toString();
	}

	public static File getLoadingErrorIcon() {
		return DoodleFiles.getFilePath("/img/loading-error.png");
	}

	/**
	 * Osterei
	 * 
	 * @return
	 */
	public static String getOeImageFilePath() {
		return DoodleFiles.getFilePath("/img/oe-tile.gif").toURI().toString();
	}

}
