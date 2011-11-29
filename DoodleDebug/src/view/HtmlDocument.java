package view;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.management.RuntimeErrorException;

import html_generator.Tag;

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
		String result = "";
		// load from file "style.css"
		try {
			Scanner sc = new Scanner(new File("./style.css"));
			while (sc.hasNext()) {
				result += sc.next();
			}
		} catch (FileNotFoundException e) {
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
