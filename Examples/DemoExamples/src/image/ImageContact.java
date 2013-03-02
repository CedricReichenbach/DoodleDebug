package image;

import java.awt.Image;

import ch.unibe.scg.doodle.api.DoodleCanvas;
import ch.unibe.scg.doodle.api.Doodleable;

public class ImageContact implements Doodleable {
	
	private String name;
	private String place;
	private Image image;
	private String phone;

	public ImageContact(String name, String place, Image image, String phone) {
		this.name = name;
		this.place = place;
		this.image = image;
		this.phone = phone;
	}

	@Override
	public void doodleOn(DoodleCanvas c) {
		c.draw(image);
		c.newColumn();
		c.draw(name);
		c.newLine();
		c.draw(place);
		c.newLine();
		c.draw(phone);
	}

	@Override
	public void summarizeOn(DoodleCanvas c) {
		c.draw(name);
	}

}
