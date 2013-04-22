package demo_scg.plugin;

import java.util.HashSet;
import java.util.Set;

import ch.unibe.scg.doodle.htmlgen.Tag;
import ch.unibe.scg.doodle.plugins.AbstractPlugin;
import ch.unibe.scg.doodle.rendering.DoodleRenderException;
import demo_scg.model.PhoneNumber;

public class PhoneNumberPlugin extends AbstractPlugin {

	@Override
	public Set<Class<?>> getDrawableClasses() {
		HashSet<Class<?>> hs = new HashSet<>();
		hs.add(PhoneNumber.class);
		return hs;
	}

	@Override
	public void render(Object object, Tag tag) throws DoodleRenderException {
		PhoneNumber phoneNumber = (PhoneNumber)object;
		
		Tag wrapper = new Tag("div", "class=wrapper");
		wrapper.add(phoneNumber.toString());
		tag.add(wrapper);
	}

	@Override
	public void renderSimplified(Object object, Tag tag)
			throws DoodleRenderException {
		PhoneNumber phoneNumber = (PhoneNumber)object;
		
		tag.add(phoneNumber.toString());
	}
	
	public String getCSS() {
		return ".PhoneNumberPlugin {background-color: gold; padding: 1em;}" +
				".PhoneNumberPlugin .wrapper {background-color: black; color: white;}";
	}

}
