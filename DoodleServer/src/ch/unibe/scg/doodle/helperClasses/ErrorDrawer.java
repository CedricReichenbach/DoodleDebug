package ch.unibe.scg.doodle.helperClasses;

import ch.unibe.scg.doodle.api.DoodleCanvas;
import ch.unibe.scg.doodle.api.Doodleable;

public class ErrorDrawer implements Doodleable {

	private final Exception exception;

	public ErrorDrawer(Exception e) {
		this.exception = e;
	}

	@Override
	public void drawOn(DoodleCanvas c) {
		c.draw(this.exception.getStackTrace());
		exception.printStackTrace();
	}

	@Override
	public void summarizeOn(DoodleCanvas c) {
		c.draw(this.exception);
	}

}
