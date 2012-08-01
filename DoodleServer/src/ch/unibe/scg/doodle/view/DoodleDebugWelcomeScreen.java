package ch.unibe.scg.doodle.view;

import java.util.LinkedList;
import java.util.List;

import ch.unibe.scg.doodle.htmlgen.Tag;

public class DoodleDebugWelcomeScreen extends HtmlDocument {

	public DoodleDebugWelcomeScreen() {
		super();
		this.body = makeBody();
	}

	@SuppressWarnings("unchecked")
	private Tag makeBody() {
		Tag body = new Tag("body");
		Tag welcome = new Tag("h1", "id=welcomeTitle");
		welcome.add("Welcome to DoodleDebug!");
		body.add(welcome);
		return body;
	}

	@Override
	protected String makePluginStyle() {
		return super.makePluginStyle()
				+ "\n#welcomeTitle{text-shadow: 1px 1px 0 white; "
				+ "filter: progid:DXImageTransform.Microsoft.DropShadow(OffX=1, OffY=1, Color=white);}";
	}

	@Override
	protected List<String> getJSFiles() {
		return new LinkedList<String>();
	}
}
