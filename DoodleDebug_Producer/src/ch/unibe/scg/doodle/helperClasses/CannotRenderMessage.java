package ch.unibe.scg.doodle.helperClasses;

import ch.unibe.scg.doodle.api.DoodleCanvas;
import ch.unibe.scg.doodle.api.Doodleable;

public class CannotRenderMessage implements Doodleable {

	private String className;

	public CannotRenderMessage(String canonicalName) {
		this.className = canonicalName;
	}

	@Override
	public void doodleOn(DoodleCanvas c) {
		c.draw("Could not render: " + className);
		c.newLine();
		c.draw("Possible reasons:");
		c.newLine();
		c.draw("- Inconsistent objects (also in nested ones)");
		c.newLine();
		c.draw(" - Running thread sensitive operations (e.g. IO)");
	}

	@Override
	public void summarizeOn(DoodleCanvas c) {
		c.draw("Could not render: " + className);
	}

}
