package ch.unibe.scg.doodle.util;

public class JavascriptCallsUtil {
	public static String showInLightboxCall(String html) {
		return "showInLightbox('" + escape(html) + "')";
	}

	public static String addToBodyCall(String html) {
		return "addCode('" + escape(html) + "')";
	}

	public static String addCSS(String css) {
		return "addCSS('" + escape(css) + "')";
	}

	/**
	 * Let single quotes (') escape in order to prevent confusion.
	 * 
	 * @param html
	 * @return
	 */
	private static String escape(String html) {
		String escaped = html.replace("\\", "\\\\").replace("\t", "\\t")
				.replace("\b", "\\b").replace("\n", "\\n").replace("\r", "\\r")
				.replace("\f", "\\f").replace("\'", "\\'")
				.replace("\"", "\\\"");
		return escaped;
	}

	public static String insertImgSrc(int id, String base64) {
		return "insertImageSrc(" + id + ",'" + base64 + "')";
	}
}
