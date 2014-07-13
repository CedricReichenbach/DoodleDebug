package ch.unibe.scg.doodle.view;

import java.util.List;

import ch.unibe.scg.doodle.htmlgen.Tag;
import ch.unibe.scg.doodle.server.util.DoodleFiles;
import ch.unibe.scg.doodle.server.util.DoodleImages;
import ch.unibe.scg.doodle.server.views.DoodleLocationCodes;

public class DoodleDebugWelcomeScreen extends HtmlDocument {

	private static final String DD_WIKI = "http://scg.unibe.ch/wiki/projects/doodledebug";

	public DoodleDebugWelcomeScreen() {
		super();
		this.body = makeBody();
		this.setBackgroundImage();
	}

	private void setBackgroundImage() {
		String texPath = DoodleImages.getDoodleTextureImageFilePath();
		body.addAttribute("style", "background-image:url(" + texPath + ")");
	}

	@SuppressWarnings("unchecked")
	@Override
	protected Tag makeHead() {
		Tag head = super.makeHead();
		Tag js = new Tag("script", "src=js/tutorial.js");
		head.add(js);
		return head;
	}

	@SuppressWarnings("unchecked")
	private Tag makeBody() {
		Tag body = new Tag("body");
		body.addAttribute("onload", "appearWelcome()");
		Tag background = new Tag("div", "id=background");
		Tag welcome = new Tag("h1", "id=welcomeTitle");
		welcome.add("Welcome to DoodleDebug!");
		welcome.addAttribute("style", "display:none");
		background.add(welcome);

		background.add(Tag.hr());

		Tag shortTut = new Tag("div", "id=shortTut");
		Tag shortTutTitle = new Tag("h3");
		shortTutTitle.add("Ultra-short Tutorial:");
		shortTut.add(shortTutTitle);
		Tag code = new Tag("code");
		code.add("Doo.dle(object);");
		shortTut.add(code);
		shortTut.addAttribute("style", "display:none");
		background.add(shortTut);

		background.add(Tag.hr());

		Tag fullTutorial = new Tag("h3", "id=tutorial", "style=display:none");
		Tag tutLink = new Tag("a");
		tutLink.addAttribute("href", DoodleLocationCodes.EXTERNAL_LINK_PREFIX
				+ DoodleFiles.getResolvedFileURL("tutorials/dd-tutorial.html")
						.toExternalForm());
		tutLink.add("Complete Tutorial");
		fullTutorial.addAttribute("style", "display:none");
		fullTutorial.add(tutLink);
		background.add(fullTutorial);

		background.add(Tag.hr());

		Tag info = new Tag("div", "id=info");
		Tag infoLink = new Tag("a", "href="
				+ DoodleLocationCodes.EXTERNAL_LINK_PREFIX + DD_WIKI);
		infoLink.add(DD_WIKI);
		info.add(infoLink);
		info.addAttribute("style", "display:none");
		background.add(info);

		body.add(background);
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
