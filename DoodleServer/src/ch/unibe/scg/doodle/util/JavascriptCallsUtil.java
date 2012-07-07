package ch.unibe.scg.doodle.util;

public class JavascriptCallsUtil {
	public static String showInLightbox(String html) {
		return "showInLightbox('" + escape(html) + "')";
	}

	/**
	 * Let single quotes (') escape in order to prevent confusion.
	 * @param html
	 * @return
	 */
	private static String escape(String html) {
		html.replaceAll("'", "\\'");
		return null;
	}
}
