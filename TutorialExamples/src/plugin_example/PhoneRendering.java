package plugin_example;

import ch.unibe.scg.doodle.htmlgen.Tag;
import ch.unibe.scg.doodle.rendering.DoodleRenderException;
import ch.unibe.scg.doodle.rendering.Rendering;

public class PhoneRendering implements Rendering<Phone> {

	@Override
	public void render(Phone phone, Tag tag) throws DoodleRenderException {
		tag.add("A Phone: ");
		Tag number = new Tag("span", "class=phone-number");
		number.add("++" + phone.getPre());
		number.add(phone.getNumber());
		tag.add(number);
	}

	@Override
	public void renderSmall(Phone phone, Tag tag) throws DoodleRenderException {
		tag.add(phone.getPre() + phone.getNumber());
	}

}
