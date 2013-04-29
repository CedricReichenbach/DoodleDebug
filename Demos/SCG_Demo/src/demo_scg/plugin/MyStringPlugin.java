package demo_scg.plugin;

import java.util.HashSet;
import java.util.Set;

import ch.unibe.scg.doodle.htmlgen.Tag;
import ch.unibe.scg.doodle.plugins.AbstractPlugin;
import ch.unibe.scg.doodle.rendering.DoodleRenderException;

public class MyStringPlugin extends AbstractPlugin {

	@Override
	public Set<Class<?>> getDrawableClasses() {
		HashSet<Class<?>> hs = new HashSet<>();
		hs.add(String.class);
		return hs;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void render(Object object, Tag tag) throws DoodleRenderException {
		String string = (String) object;

		tag.add(string);
		Tag charNumber = new Tag("span", "class=charNumber");
		charNumber.add(string.length());
		tag.add(charNumber);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void renderSimplified(Object object, Tag tag)
			throws DoodleRenderException {
		String string = (String) object;

		tag.add(string);
	}

	@Override
	public String getCSS() {
		String classAttribute = this.getClassAttribute();
		return "."
				+ classAttribute
				+ " {border: 2px solid gold; overflow: hidden; font-family: monospace;}"
				+ "."
				+ classAttribute
				+ " .charNumber"
				+ "{margin-left: 1em; background-color: black; color: gold; padding: 2px;}";
	}

}
