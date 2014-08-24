package ch.unibe.scg.doodle.server.util;

public class DoodleImages {

	public static String getDDIconPath() {
		return DoodleFiles.getFileURI("/icons/doodledebug-icon.png").toString();
	}

	public static String getCloseWindowImageFilePath() {
		return DoodleFiles.getFileURI("/img/close-button_16.png").toString();
	}

	public static String getDoodleTextureImageFilePath() {
		return DoodleFiles.getFileURI("/img/dd-tex.png").toString();
	}

	/**
	 * Osterei
	 * 
	 * @return
	 */
	public static String getOeImageFilePath() {
		return DoodleFiles.getFileURI("/img/oe-tile.gif").toString();
	}
}
