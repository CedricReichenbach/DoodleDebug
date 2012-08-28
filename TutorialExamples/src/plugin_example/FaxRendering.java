package plugin_example;

import ch.unibe.scg.doodle.api.DoodleRenderer;
import ch.unibe.scg.doodle.htmlgen.Tag;
import ch.unibe.scg.doodle.rendering.DoodleRenderException;
import ch.unibe.scg.doodle.rendering.Rendering;

public class FaxRendering implements Rendering<Fax> {

	@Override
	public void render(Fax fax, Tag tag) throws DoodleRenderException {
		tag.add("A Fax: ");
		Tag number = new Tag("div", "class=fax-number");
		DoodleRenderer.renderInto(fax.getNumber(), number, false);
		tag.add(number);
	}

	@Override
	public void renderSmall(Fax fax, Tag tag) throws DoodleRenderException {
		tag.add(fax.getNumber());
	}

}
