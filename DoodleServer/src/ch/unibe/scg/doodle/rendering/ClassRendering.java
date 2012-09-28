package ch.unibe.scg.doodle.rendering;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;

import ch.unibe.scg.doodle.Doodler;
import ch.unibe.scg.doodle.htmlgen.Tag;

public class ClassRendering implements Rendering<Class<?>> {

	@Inject
	Doodler doodler;

	@Override
	public void render(Class<?> classObject, Tag tag) {
		List<Field> fields = Arrays.asList(classObject.getDeclaredFields());
		for (Field f : fields) {
			Tag wrapper = new Tag("div", "class=wrapper");
			renderField(f, wrapper);
			tag.add(wrapper);
		}
		List<Method> methods = Arrays.asList(classObject.getDeclaredMethods());
		for (Method m : methods) {
			Tag wrapper = new Tag("div", "class=wrapper");
			renderMethod(m, tag);
			tag.add(wrapper);
		}
		List<Class<?>> innerClasses = Arrays.asList(classObject
				.getDeclaredClasses());
		for (Class c : innerClasses) {
			Tag inner = new Tag("div", "class=innerClass");
			doodler.renderInlineInto(c, inner);
			tag.add(inner);
		}
	}

	private void renderField(Field field, Tag tag) {
		FieldDoodlerRendering.renderScope(field, tag);
		Tag element = new Tag("div", "class=element");
		element.addCSSClass("field");
		element.add(field.getName());
		tag.add(element);
	}

	private void renderMethod(Method method, Tag tag) {
		FieldDoodlerRendering.renderScope(method, tag);
		Tag element = new Tag("div", "class=element");
		element.addCSSClass("method");

		// return type
		Class<?> returnType = method.getReturnType();
		element.add(returnType.getSimpleName()+" ");

		// name & arguments
		element.add(method.getName() + "(");
		List<Class<?>> arguments = Arrays.asList(method.getParameterTypes());
		Iterator<Class<?>> it = arguments.iterator();
		while (it.hasNext()) {
			Class<?> argType = it.next();
			element.add(argType.getSimpleName());
			if (it.hasNext())
				element.add(", ");
		}
		element.add(")");
		tag.add(element);
	}

	@Override
	public void renderSimplified(Class<?> classObject, Tag tag) {
		tag.add(classObject.getName());
	}

}
