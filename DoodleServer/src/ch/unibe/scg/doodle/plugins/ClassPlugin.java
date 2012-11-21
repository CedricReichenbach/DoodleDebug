package ch.unibe.scg.doodle.plugins;

import java.lang.reflect.Modifier;
import java.util.HashSet;
import java.util.Set;

import javax.inject.Inject;

import ch.unibe.scg.doodle.htmlgen.Tag;
import ch.unibe.scg.doodle.rendering.ClassRendering;

public class ClassPlugin extends AbstractPlugin {

	@Inject
	ClassRendering classRendering;

	@Override
	public Set<Class<?>> getDrawableClasses() {
		Set<Class<?>> hs = new HashSet<Class<?>>();
		hs.add(Class.class);
		return hs;
	}

	@Override
	public void render(Object classObject, Tag tag) {
		classRendering.render((Class<?>) classObject, tag);
	}

	@Override
	public void renderSimplified(Object classObject, Tag tag) {
		classRendering.renderSimplified((Class<?>) classObject, tag);
	}

	@Override
	public String getCSS() {
		return wrapperCSS() + elementCSS()
				+ FieldDoodlerPlugin.scopeCSS("ClassPlugin")
				+ ".ClassPlugin .static .scope {}";
	}

	private String wrapperCSS() {
		return ".ClassPlugin .wrapper {margin: 3px 0; white-space: nowrap;}";
	}

	private String elementCSS() {
		return ".ClassPlugin .element {border: 1px dotted #ccc; display: inline-block; padding: 2px 1px 0 1px; min-height: 1.5em; background-color: whitesmoke;}"
				+ ".ClassPlugin .method {border-style: dashed;}"
				+ ".ClassPlugin .name {font-weight:500;}"
//				+ ".ClassPlugin .static .name {text-decoration: underline;}"
				+ ".ClassPlugin .returnType, .ClassPlugin .fieldType {color: #444; margin-left: 1px;}"
				+ ".ClassPlugin .returnType {font-style: italic;}"
				+ ".ClassPlugin .arguments {font-style: italic;}";
	}

	@Override
	public String getObjectTypeName(Object o) {
		Class<?> c = (Class<?>) o;
		String result = "";
		int mod = c.getModifiers();
		if (Modifier.isAbstract(mod) && !Modifier.isInterface(mod))
			result += "abstract ";
		result += (c.isInterface() ? "Interface" : "Class") + "&#60;"
				+ c.getSimpleName() + "&#62;";
		return result;
	}
}
