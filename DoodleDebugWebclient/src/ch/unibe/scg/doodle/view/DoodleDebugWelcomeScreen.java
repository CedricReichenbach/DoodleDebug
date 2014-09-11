package ch.unibe.scg.doodle.view;

import java.util.List;

import ch.unibe.scg.doodle.htmlgen.Tag;
import ch.unibe.scg.doodle.server.util.DoodleFiles;

class DoodleDebugWelcomeScreen extends HtmlDocument {

	private static final String DD_WIKI = "http://scg.unibe.ch/wiki/projects/doodledebug";

	public DoodleDebugWelcomeScreen() {
		super();
	}

	@Override
	protected Tag makeHead() {
		Tag head = super.makeHead();
		return head;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected Tag makeBody() {
		Tag body = new Tag("body");
		body.addAttribute("onload", "appearWelcome()");
		Tag welcome = new Tag("h1", "id=welcomeTitle");
		welcome.add("Welcome to DoodleDebug!");
		welcome.addAttribute("style", "display:none");
		body.add(welcome);

		body.add(Tag.hr());

		Tag fullTutorial = new Tag("div", "id=tutorial", "style=display:none");
		Tag title = new Tag("h2");
		title.add("How to doodle objects");
		fullTutorial.add(title);
		Tag code = new Tag("code");
		code.add("Doo.dle(object);");
		fullTutorial.add(code);
		fullTutorial.addAttribute("style", "display:none");
		Tag tutLink = new Tag("a", "href="
				+ DoodleFiles.getLink("tutorials/dd-tutorial.html"),
				"target=_blank");
		tutLink.add("Tutorial & Documentation");
		fullTutorial.add(tutLink);
		body.add(fullTutorial);

		body.add(Tag.hr());

		Tag info = new Tag("div", "id=info");
		Tag infoLink = new Tag("a", "href=" + DD_WIKI, "target=_blank");
		infoLink.add(DD_WIKI);
		info.add(infoLink);
		body.add(info);

		return body;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void makeStyle(Tag head) {
		super.makeStyle(head);
		head.add(createCSSTag("welcome.css"));
	}

	@Override
	protected List<String> getJSFiles() {
		List<String> list = super.getJSFiles();
		list.add("welcome.js");
		return list;
	}
}
