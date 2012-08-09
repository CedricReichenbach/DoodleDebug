package ch.unibe.scg.doodle.view;

import java.net.URL;
import java.util.List;

import org.eclipse.core.resources.ResourcesPlugin;

import ch.unibe.scg.doodle.htmlgen.Tag;
import ch.unibe.scg.doodle.server.util.DoodleFiles;
import ch.unibe.scg.doodle.server.util.DoodleImages;
import ch.unibe.scg.doodle.view.css.CSSUtil;

public class DoodleDebugWelcomeScreen extends HtmlDocument {

	public DoodleDebugWelcomeScreen() {
		super();
		this.body = makeBody();
		this.setBackgroundImage();
	}

	private void setBackgroundImage() {
		String texPath = DoodleImages.getDoodleTextureImageFilePath();
		body.addAttribute("style", "background-image:url(" + texPath + ")");
	}

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
		background.add(welcome);

		background.add(Tag.hr());

		Tag info = new Tag("h3", "id=info", "style=visibility:hidden");

		Tag tutLink = new Tag("a");
		tutLink.addAttribute("href",
				DoodleFiles.getResolvedFileURL("tutorials/dd-tutorial.html")
						.toExternalForm());
		tutLink.addAttribute("target", "_blank");
		tutLink.add("Tutorial");
		info.add(tutLink);
		background.add(info);
		body.add(background);
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
