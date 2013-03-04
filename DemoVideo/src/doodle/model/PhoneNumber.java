package doodle.model;

import ch.unibe.scg.doodle.api.DoodleCanvas;
import ch.unibe.scg.doodle.api.Doodleable;

public class PhoneNumber implements Doodleable {
	private String areaCode;
	private String rest;

	public PhoneNumber(String areaCode, String rest) {
		super();
		this.areaCode = areaCode;
		this.rest = rest;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public String getRest() {
		return rest;
	}

	@Override
	public void doodleOn(DoodleCanvas c) {
		c.draw(areaCode);
		c.draw(rest);
	}

	@Override
	public void summarizeOn(DoodleCanvas c) {
		doodleOn(c);
	}
}
