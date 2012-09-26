package ch.unibe.scg.doodle.rendering;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import ch.unibe.scg.doodle.Doodler;
import ch.unibe.scg.doodle.htmlgen.Tag;

public class FieldDoodlerRendering implements Rendering<Object> {

	@Inject
	Doodler doodler;

	@Override
	public void render(Object object, Tag tag) throws DoodleRenderException {
		// Tag title = new Tag("h3", "class=title");
		// title.add("Fields: ");
		// tag.add(title);

		List<Field> fields = Arrays.asList(object.getClass()
				.getDeclaredFields());
		for (Field field : fields) {
			field.setAccessible(true);
			renderField(object, field, tag);
		}
	}

	private void renderField(Object object, Field field, Tag tag)
			throws DoodleRenderException {
		// don't render static ones
		if (Modifier.isStatic(field.getModifiers())) {
			return;
		}

		Tag wrapper = new Tag("div", "class=fieldWrapper");
		Tag div = new Tag("div", "class=field");
		renderScope(field, wrapper);
		Tag name = new Tag("div", "class=name");
		name.add(field.getName() + ":");
		div.add(name);
		Tag content = new Tag("div", "class=content");
		try {
			doodler.renderInlineInto(field.get(object), content);
			div.add(content);
		} catch (IllegalArgumentException e) {
			throw new DoodleRenderException(e);
		} catch (IllegalAccessException e) {
			throw new DoodleRenderException(e);
		}
		wrapper.add(div);
		tag.add(wrapper);
	}

	private void renderScope(Field field, Tag tag) {
		Tag scope = new Tag("div", "class=scope");
		Tag p = new Tag("p");

		int mod = field.getModifiers();
		if (Modifier.isPublic(mod)) {
			scope.addCSSClass("public");
			p.add("+");
		} else if (Modifier.isProtected(mod)) {
			scope.addCSSClass("protected");
			p.add("#");
		} else if (Modifier.isPrivate(mod)) {
			scope.addCSSClass("private");
			p.add("-");
		} else { // default/package scope
			scope.addCSSClass("default");
			p.add("~");
		}

		if (Modifier.isStatic(mod)) {
			tag.addCSSClass("static");
		}

		scope.add(p);
		tag.add(scope);
	}

	@Override
	public void renderSimplified(Object object, Tag tag) {
		/*
		 * XXX Idea: only colored dots for fields (e.g. 4*4 px, similar to
		 * collections)
		 */
		tag.add("Object containing " + object.getClass().getFields().length
				+ " fields");
	}
}
