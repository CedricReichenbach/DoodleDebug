package ch.unibe.scg.doodle.view;

import java.util.ArrayList;
import java.util.List;

public class CSSCollection extends ArrayList<String> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static CSSCollection instance;
	private static List<String> old = new ArrayList<String>();

	public static CSSCollection instance() {
		if (instance == null) {
			instance = new CSSCollection();
		}
		return instance;
	}

	private static String getAllCSS() {
		String all = "";
		for (String rules : CSSCollection.instance()) {
			all += rules + "\n";
		}
		return all;
	}

	/**
	 * Return all CSS and remove everything.
	 * 
	 * @return
	 */
	public static String flushAllCSS() {
		String css = getAllCSS();
		old.addAll(instance);
		CSSCollection.instance = null;
		return css;
	}

	@Override
	public boolean add(String css) {
		if (this.contains(css) || old.contains(css))
			return false; // prevent duplication
		return super.add(css);
	}

	public static void reset() {
		old = new ArrayList<String>();
		instance = null;
	}
}
