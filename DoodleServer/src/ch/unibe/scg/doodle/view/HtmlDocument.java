package ch.unibe.scg.doodle.view;

import java.util.LinkedList;
import java.util.List;

import ch.unibe.scg.doodle.htmlgen.Tag;
import ch.unibe.scg.doodle.util.FileUtil;
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
	protected Tag makeHead() {
		Tag head = new Tag("head");
		head.add("<meta charset=\"utf-8\">");
		head.add("<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\" >"); // for
																				// IE9
																				// compatibility
		Tag title = new Tag("title");
		title.add("DoodleDebug");
		head.add(title);
		makeStyle(head);
		makeJavascript(head);
		return head;
	}

	@SuppressWarnings("unchecked")
	protected void makeStyle(Tag head) {
		head.add(createCSSTag("style.css"));
		head.add(createCSSTag("lightbox.css"));

		Tag pluginStyle = new Tag("style", "type=text/css");
		pluginStyle.add(makePluginStyle());
		head.add(pluginStyle);
	}

	@SuppressWarnings("unchecked")
	protected Tag createCSSTag(String filename) {
		// XXX: Link only?
		Tag style = new Tag("style", "type=text/css");
		style.add(FileUtil.readFile(CSSUtil.getCSSURLFromFile(filename)));
		return style;
	}

	protected String makePluginStyle() {
		// provided by plugins
		return CSSCollection.flushAllCSS();
	}

	@SuppressWarnings("unchecked")
	private void makeJavascript(Tag head) {
		List<String> jsFiles = getJSFiles();
		for (String file : jsFiles) {
			Tag js = new Tag("script", "type=text/javascript");
			js.addAttribute("src", JSUtil.getJSURLFromFile(file)
					.toExternalForm());
			// XXX Write it inline into tag (instead of linking)?
			head.add(js);
		}
	}

	/**
	 * Loads JS code from files. Be careful: Order matters (I guess).
	 * 
	 * @return
	 */
	protected List<String> getJSFiles() {
		List<String> js = new LinkedList<String>();
		js.add("doodleDebug.js");
		js.add("prototype.js"); // uncompressed
								// too slow! (>
								// 1s)
		js.add("scriptaculous.js");
		js.add("lightbox.js");
		js.add("builder.js");
		js.add("effects.js");
		js.add("controls.js");
		js.add("dragdrop.js");
		js.add("slider.js");
		js.add("sound.js");
		js.add("testExamples.js"); // XXX Only when
									// developing
		return js;
	}

	public void setBody(Tag body) {
		this.body = body;
	}

	public String toString() {
		return "" + doctype + head + body;
	}
}
