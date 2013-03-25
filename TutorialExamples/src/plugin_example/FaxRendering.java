package plugin_example;

import ch.unibe.scg.doodle.api.DoodleRenderer;
import ch.unibe.scg.doodle.htmlgen.Tag;
import ch.unibe.scg.doodle.rendering.DoodleRenderException;
import ch.unibe.scg.doodle.rendering.Rendering;

public class FaxRendering implements Rendering<Fax> {

	@SuppressWarnings("unchecked")
	@Override
	public void render(Fax fax, Tag tag) throws DoodleRenderException {
		tag.add("A Fax: ");
		Tag number = new Tag("div", "class=fax-number");
		DoodleRenderer.renderInto(fax.getNumber(), number, false);
		tag.add(number);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void renderSimplified(Fax fax, Tag tag) throws DoodleRenderException {
		tag.add(fax.getNumber());
	}
}