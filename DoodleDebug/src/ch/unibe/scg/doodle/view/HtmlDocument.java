package ch.unibe.scg.doodle.view;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import ch.unibe.ch.scg.htmlgen.Tag;

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
		header.add("<meta http-equiv=\"X-UA-Compatible\" content=\"IE=100\" >"); // for IE9 compatibility
		Tag title = new Tag("title");
		title.add("DoodleDebug");
		header.add(title);
		header.add(makeStyle());
		return header;
	}

	@SuppressWarnings("unchecked")
	private Tag makeStyle() {
		Tag style = new Tag("style");
		style.add(makeStyleSheet());
		return style;
	}

	private String makeStyleSheet() {
		// load from file "style.css"
		String css = readFile(this.getClass().getResource("style.css"));
		// + provided by plugins
		return css + CSSCollection.getAllCSS();
	}

	private String readFile(URL url) {
		String result = "";
		
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
			String inputLine = "";
			while (inputLine != null) {
				result += inputLine+"\n";
				inputLine = in.readLine();
			}
			in.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		
		return result;
	}

	public void setBody(Tag body) {
		this.body = body;
	}

	public String toString() {
		return "" + doctype + header + body;
	}
}
