package ch.unibe.scg.doodle.view;

import ch.unibe.scg.doodle.htmlgen.Tag;
import ch.unibe.scg.doodle.view.css.CSSUtil;
import ch.unibe.scg.doodle.view.js.JSUtil;

public class HtmlDocument {

	private Tag head;
	private String doctype;
	protected Tag body;

	public HtmlDocument() {
		this.head = makeHead();
		this.doctype = makeDoctype();
		this.body = new Tag("body");
	}

	private String makeDoctype() {
		return "<!DOCTYPE html>\n";
	}

	@SuppressWarnings("unchecked")
	private Tag makeHead() {
		Tag header = new Tag("head");
		header.add("<meta charset=\"utf-8\">");
		header.add("<meta http-equiv=\"X-UA-Compatible\" content=\"IE=100\" >"); // for
																					// IE9
																					// compatibility
		Tag title = new Tag("title");
		title.add("DoodleDebug");
		header.add(title);
		header.add(makeStyle());
		header.add(makeJavascript());
		return header;
	}

	@SuppressWarnings("unchecked")
	private Tag makeStyle() {
		Tag style = new Tag("style", "type=text/css");
		style.add(makeStyleSheet());
		return style;
	}

	@SuppressWarnings("unchecked")
	private Tag makeJavascript() {
		Tag js = new Tag("script", "type=text/javascript");
		js.add(getJS());
		return js;
	}

	protected String makeStyleSheet() {
		// load from file "style.css"
		String css = CSSUtil.getCSSFromFile("style.css");
		// load from lightbox css
		css += CSSUtil.getCSSFromFile("lightbox.css");
		// + provided by plugins
		return css + CSSCollection.getAllCSS();
	}

	/**
	 * Loads JS code from files. Be careful: Order matters (I guess).
	 * 
	 * @return
	 */
	protected String getJS() {
		String js = "";
		long before = System.currentTimeMillis();
		js += JSUtil.getJSFromFile("doodleDebug.js");
		js += JSUtil.getJSFromFile("prototype.js"); // uncompressed
													// too slow! (>
													// 1s)
		js += JSUtil.getJSFromFile("scriptaculous.js");
		js += JSUtil.getJSFromFile("lightbox.js");
		js += JSUtil.getJSFromFile("builder.js");
		js += JSUtil.getJSFromFile("effects.js");
		js += JSUtil.getJSFromFile("controls.js");
		js += JSUtil.getJSFromFile("dragdrop.js");
		js += JSUtil.getJSFromFile("slider.js");
		js += JSUtil.getJSFromFile("sound.js");
		js += JSUtil.getJSFromFile("testExamples.js"); // XXX Only when
														// developing
		long after = System.currentTimeMillis();
		System.out.println("Time to load JS: " + (after - before) + " ms");
		return js;
	}

	public void setBody(Tag body) {
		this.body = body;
	}

	public String toString() {
		return "" + doctype + head + body;
	}
}
