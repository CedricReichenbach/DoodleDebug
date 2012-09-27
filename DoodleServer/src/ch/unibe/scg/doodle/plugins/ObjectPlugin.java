package ch.unibe.scg.doodle.plugins;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashSet;
import java.util.Set;

import javax.inject.Inject;

import ch.unibe.scg.doodle.htmlgen.Tag;
import ch.unibe.scg.doodle.rendering.DoodleRenderException;

/**
 * Represents default drawing of any Object, if no other is defined.
 * 
 * @author Cedric Reichenbach
 * 
 */
public class ObjectPlugin extends AbstractPlugin {

	@Inject
	FieldDoodlerPlugin fieldDoodlerPlugin;

	@Inject
	StringPlugin stringPlugin;

	@Override
	public Set<Class<?>> getDrawableClasses() {
		HashSet<Class<?>> hs = new HashSet<Class<?>>(1);
		hs.add(Object.class);
		return hs;
	}

	private final int MAX_FIELDS = 7;

	@Override
	public void render(Object object, Tag tag) throws DoodleRenderException {
		if (insideMax(object) && !isNumber(object))
			fieldDoodlerPlugin.render(object, tag);
		else
			stringPlugin.render(object.toString(), tag);
	}

	private boolean isNumber(Object object) {
		return object instanceof Number;
	}

	boolean insideMax(Object object) {
		return numberOfNonstaticFields(object) <= MAX_FIELDS;
	}

	private int numberOfNonstaticFields(Object object) {
		Field[] fields = object.getClass().getDeclaredFields();
		int counter = 0;
		for (Field f : fields) {
			if (!Modifier.isStatic(f.getModifiers()))
				counter++;
		}
		return counter;
	}

	@Override
	public void renderSimplified(Object object, Tag tag) {
		fieldDoodlerPlugin.renderSimplified(object, tag);
	}

	@Override
	public String getClassAttribute() {
		return fieldDoodlerPlugin.getClassAttribute() + " "
				+ stringPlugin.getClassAttribute();
	}

	@Override
	public String getCSS() {
		return fieldDoodlerPlugin.getCSS() + stringPlugin.getCSS();
	}

}
