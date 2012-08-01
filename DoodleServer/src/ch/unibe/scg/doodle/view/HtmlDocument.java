package ch.unibe.scg.doodle.view;

import java.net.URL;

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
		Tag head = new Tag("head");
		head.add("<meta charset=\"utf-8\">");
		head.add("<meta http-equiv=\"X-UA-Compatible\" content=\"IE=100\" >"); // for
																				// IE9
																				// compatibility
		Tag title = new Tag("title");
		title.add("DoodleDebug");
		head.add(title);
		makeStyle(head);
		head.add(makeJavascript());
		return head;
	}

	@SuppressWarnings("unchecked")
	private void makeStyle(Tag head) {
		// from file "style.css"
		URL mainStyle = CSSUtil.getCSSURLFromFile("style.css");
		Tag main = new Tag("link", "rel=stylesheet", "type=text/css");
		main.addAttribute("href", mainStyle.toExternalForm());
		head.add(main);
		// from lightbox.css
		URL lightboxStyle = CSSUtil.getCSSURLFromFile("lightbox.css");
		Tag lightbox = new Tag("link", "rel=stylesheet", "type=text/css");
		lightbox.addAttribute("href", lightboxStyle.toExternalForm());
		head.add(lightbox);

		Tag pluginStyle = new Tag("style", "type=text/css");
		pluginStyle.add(makePluginStyle());
		head.add(pluginStyle);
	}

	protected String makePluginStyle() {
		// provided by plugins
		return CSSCollection.flushAllCSS();
	}

	@SuppressWarnings("unchecked")
	private Tag makeJavascript() {
		Tag js = new Tag("script", "type=text/javascript");
		js.add(getJS());
		return js;
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
