package ch.unibe.scg.doodle.view;

import java.net.URL;
import java.util.List;

import ch.unibe.scg.doodle.htmlgen.Tag;
import ch.unibe.scg.doodle.view.css.CSSUtil;

public class DoodleDebugWelcomeScreen extends HtmlDocument {

	public DoodleDebugWelcomeScreen() {
		super();
		this.body = makeBody();
	}

	@SuppressWarnings("unchecked")
	private Tag makeBody() {
		Tag body = new Tag("body");
		body.addAttribute("onload", "appearWelcome()");
		Tag welcome = new Tag("h1", "id=welcomeTitle");
		welcome.add("Welcome to DoodleDebug!");
		body.add(welcome);

		body.add(Tag.hr());

		Tag info = new Tag("h3", "id=info", "style=visibility:hidden");
		Tag tutLink = new Tag("a", "href=http://scg.unibe.ch/doodledebug",
				"target=_blank");
		tutLink.add("scg.unibe.ch/doodledebug");
		info.add("Tutorials: " + tutLink);
		body.add(info);
		return body;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void makeStyle(Tag head) {
		super.makeStyle(head);
		URL welcomeCSS = CSSUtil.getCSSURLFromFile("welcome.css");
		Tag main = new Tag("link", "rel=stylesheet", "type=text/css");
		main.addAttribute("href", welcomeCSS.toExternalForm());
		head.add(main);
	}
	
	@Override
	protected List<String> getJSFiles() {
		List<String> list = super.getJSFiles();
		list.add("welcome.js");
		return list;
	}
}
