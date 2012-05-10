package ch.unibe.scg.doodle.view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import ch.unibe.ch.scg.htmlgen.Tag;
import ch.unibe.scg.doodle.view.css.CSSUtil;
import ch.unibe.scg.doodle.view.js.JSUtil;

public class HtmlDocument {

	private Tag header;
	private String doctype;
	private Tag body;

	public HtmlDocument() {
		this.header = makeHeader();
		this.doctype = makeDoctype();
		this.body = new Tag("body");
	}

	private String makeDoctype() {
		return "<!DOCTYPE html>\n";
	}

	@SuppressWarnings("unchecked")
	private Tag makeHeader() {
		Tag header = new Tag("head");
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

	private Object makeJavascript() {
		Tag js = new Tag("script", "type=text/javascript");
		js.add(getJSCode());
		return js;
	}

	private String makeStyleSheet() {
		// load from file "style.css"
		String css = CSSUtil.getCSSFromFile("style.css");
		// load from lightbox css
		css += CSSUtil.getCSSFromFile("lightbox.css");
		// css += CSSUtil.getCSSFromFile("screen.css");
		// + provided by plugins
		return css + CSSCollection.getAllCSS();
	}

	private String getJSCode() {
		String js = "";
		js += JSUtil.getJSFromFile("jquery-1.7.2.min.js");
		// js += JSUtil.getJSFromFile("jquery-ui-1.8.18.custom.min.js");
		// js += JSUtil.getJSFromFile("jquery.smooth-scroll.min.js");
		js += JSUtil.getJSFromFile("lightbox.js");
		return js;
	}

	public void setBody(Tag body) {
		this.body = body;
	}

	public String toString() {
		return "" + doctype + header + body;
	}
}
