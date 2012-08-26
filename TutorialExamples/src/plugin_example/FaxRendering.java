package plugin_example;

import ch.unibe.scg.doodle.htmlgen.Tag;
import ch.unibe.scg.doodle.rendering.DoodleRenderException;
import ch.unibe.scg.doodle.rendering.Rendering;

public class FaxRendering implements Rendering<Fax> {

	@Override
	public void render(Fax fax, Tag tag) throws DoodleRenderException {
		tag.add("A Fax: ");
		Tag number = new Tag("span", "class=fax-number");
		number.add(fax.getNumber());
		tag.add(number);
	}

	@Override
	public void renderSmall(Fax fax, Tag tag) throws DoodleRenderException {
		tag.add(fax.getNumber());
	}

}
