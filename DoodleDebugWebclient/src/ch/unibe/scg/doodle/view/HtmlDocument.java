package ch.unibe.scg.doodle.view;

import java.util.LinkedList;
import java.util.List;

import ch.unibe.scg.doodle.htmlgen.Tag;
import ch.unibe.scg.doodle.properties.DoodleDebugProperties;
import ch.unibe.scg.doodle.server.util.DoodleImages;
import ch.unibe.scg.doodle.view.css.CSSUtil;
import ch.unibe.scg.doodle.view.js.JSUtil;

public class HtmlDocument {

	private final Tag head;
	private final String doctype;
	protected final Tag body;

	public HtmlDocument() {
		this.head = makeHead();
		this.doctype = makeDoctype();
		this.body = makeBody();
	}

	@SuppressWarnings("unchecked")
	protected Tag makeBody() {
		Tag body = new Tag("body");

		Tag lightswitch = new Tag("div", "id=lightswitch");
		lightswitch.add("&#x2600;"); // ☀
		body.add(lightswitch);

		if (DoodleDebugProperties.betaMode())
			this.createBetaInfo(body);

		prepareLightbox(body);

		return body;
	}

	@SuppressWarnings("unchecked")
	private void createBetaInfo(Tag body) {
		Tag wrapper = new Tag("div", "id=betaInfoWrapper");
		Tag info = new Tag("div", "id=betaInfo");
		String email = DoodleDebugProperties.getFeedbackMailAddress();
		Tag mailto = new Tag("a", "href=mailto:" + email);
		mailto.add(email);
		info.add("<b>DoodleDebug <i>beta</i></b> - bugs & feedback to ");
		info.add(mailto);
		wrapper.add(info);
		body.add(wrapper);
	}

	@SuppressWarnings("unchecked")
	private void prepareLightbox(Tag body) {
		Tag lighboxWrapper = new Tag("div", "id=lightboxWrapper");
		lighboxWrapper.addAttribute("style", "visibility:hidden");
		Tag overlay = new Tag("div", "id=overlay");
		overlay.addAttribute("onclick", "javascript:hideLightbox()");
		lighboxWrapper.add(overlay);
		Tag lightbox = new Tag("div", "id=lightbox");
		lighboxWrapper.add(lightbox);
		String closeImgFilePath = DoodleImages.getCloseWindowImageFilePath();
		Tag closeImg = new Tag("img", "id=closeButton");
		closeImg.addAttribute("src", closeImgFilePath);
		closeImg.addAttribute("onclick", "javascript:hideLightbox()");
		lighboxWrapper.add(closeImg);
		body.add(lighboxWrapper);
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

	protected Tag createCSSTag(String filename) {
		Tag style = new Tag("link", "type=text/css", false);
		style.addAttribute("rel", "stylesheet");
		style.addAttribute("href", CSSUtil.getCSSPathFromFile(filename));
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
			js.addAttribute("src", JSUtil.getJSPathFromFile(file));
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
		js.add("doodleDebug.js");
		js.add("testExamples.js"); // XXX Only when
									// developing
		return js;
	}

	@SuppressWarnings("unchecked")
	public String toString() {
		Tag html = new Tag("html");
		html.add(head);
		html.add(body);
		return doctype + html;
	}
}
