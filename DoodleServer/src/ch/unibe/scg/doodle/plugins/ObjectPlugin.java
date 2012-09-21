package ch.unibe.scg.doodle.plugins;

import java.util.HashSet;
import java.util.Set;

import javax.inject.Inject;

import ch.unibe.scg.doodle.htmlgen.Tag;

/**
 * Represents default drawing of any Object, if no other is defined.
 * 
 * @author Cedric Reichenbach
 * 
 */
public class ObjectPlugin extends AbstractPlugin {

	@Inject
	StringPlugin stringPlugin;

	@Override
	public Set<Class<?>> getDrawableClasses() {
		HashSet<Class<?>> hs = new HashSet<Class<?>>(1);
		hs.add(Object.class);
		return hs;
	}

	@Override
	public void render(Object object, Tag tag) {
		stringPlugin.render(object.toString(), tag);
	}

	@Override
	public void renderSimplified(Object object, Tag tag) {
		stringPlugin.renderSimplified(object.toString(), tag);
	}

	@Override
	public String getClassAttribute() {
		return stringPlugin.getClassAttribute();
	}

	@Override
	public String getCSS() {
		return stringPlugin.getCSS();
	}

}
