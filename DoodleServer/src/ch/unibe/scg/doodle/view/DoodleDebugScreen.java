package ch.unibe.scg.doodle.view;

import ch.unibe.scg.doodle.htmlgen.Tag;

/**
 * Html file for initial displaying in DoodleDebug tab.
 * 
 * @author Cedric Reichenbach
 * 
 */
public class DoodleDebugScreen {
	private String doctype;
	private Tag html;

	@SuppressWarnings("unchecked")
	public DoodleDebugScreen() {
		this.doctype = doctype();
		this.html = new Tag("html");
		html.add(head());
		html.add(body());
	}

	protected String doctype() {
		return "<!DOCTYPE html>\n";
	}

	@SuppressWarnings("unchecked")
	protected Tag head() {
		Tag head = new Tag("head");
		head.add("<meta charset=\"utf-8\">");
		head.add("<meta http-equiv=\"X-UA-Compatible\" content=\"IE=100\" >"); // for
																				// IE9
																				// compatibility
		head.add(css());
		head.add(js());
		return head;
	}

	@SuppressWarnings("unchecked")
	protected Tag css() {
		Tag css = new Tag("style", "type=text/css");
		css.add("body {background-color: #ddd;}");
		return css;
	}

	protected Tag js() {
		Tag js = new Tag("script", "type=text/javascript");
		return js;
	}
	
	protected Tag body() {
		Tag body = new Tag("body");
		return body;
	}

	public String toString() {
		return doctype + html;
	}
}
