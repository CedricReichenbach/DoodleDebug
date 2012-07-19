package ch.unibe.scg.doodle.view;

import ch.unibe.scg.doodle.htmlgen.Tag;

public class DoodleDebugWelcomeScreen extends DoodleDebugScreen {

	@Override
	protected Tag body() {
		Tag body = super.body();
		body.add("Welcome to DoodleDebug!");
		return body;
	}
}
