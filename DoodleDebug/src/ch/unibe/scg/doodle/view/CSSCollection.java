package ch.unibe.scg.doodle.view;

import java.util.ArrayList;

public class CSSCollection extends ArrayList<String> {
	private static CSSCollection instance;

	public static CSSCollection instance() {
		if (instance == null) {
			instance = new CSSCollection();
		}
		return instance;
	}

	public static String getAllCSS() {
		String all = "";
		for (String rules : CSSCollection.instance()) {
			all += rules + "\n";
		}
		return all;
	}
}
