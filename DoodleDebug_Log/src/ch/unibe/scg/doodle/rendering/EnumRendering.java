package ch.unibe.scg.doodle.rendering;

import java.util.Arrays;
import java.util.List;

import ch.unibe.scg.doodle.htmlgen.Tag;

public class EnumRendering implements Rendering<Enum<?>> {

	@SuppressWarnings("unchecked")
	@Override
	public void render(Enum<?> e, Tag tag) throws DoodleRenderException {
		List<? extends Enum<?>> constants = (List<? extends Enum<?>>) Arrays
				.asList(e.getClass().getEnumConstants());
		Enum<?> selected = Enum.valueOf(e.getClass(), e.name());
		for (Enum<?> c : constants) {
			Tag constant = new Tag("div", "class=enumConstant");
			constant.add(c);
			if (selected.equals(c))
				constant.addCSSClass("active");
			tag.add(constant);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void renderSimplified(Enum<?> e, Tag tag)
			throws DoodleRenderException {
		tag.add(Enum.valueOf(e.getClass(), e.name()));
	}

}
