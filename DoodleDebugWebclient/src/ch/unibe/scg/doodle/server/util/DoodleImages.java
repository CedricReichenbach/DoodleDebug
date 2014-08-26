package ch.unibe.scg.doodle.server.util;

public class DoodleImages {

	public static String getDDIconPath() {
		return DoodleFiles.getLink("/icons/doodledebug-icon.png");
	}

	public static String getCloseWindowImageFilePath() {
		return DoodleFiles.getLink("/img/close-button_16.png");
	}

	public static String getDoodleTextureImageFilePath() {
		return DoodleFiles.getLink("/img/dd-tex.png");
	}

	/**
	 * Osterei
	 * 
	 * @return
	 */
	public static String getOeImageFilePath() {
		return DoodleFiles.getLink("/img/oe-tile.gif");
	}
}
