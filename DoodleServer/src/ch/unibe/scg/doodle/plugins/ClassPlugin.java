package ch.unibe.scg.doodle.plugins;

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
				+ FieldDoodlerPlugin.scopeCSS("ClassPlugin");
	}

	private String wrapperCSS() {
		return ".ClassPlugin .wrapper {margin: 3px 0;}";
	}

	private String elementCSS() {
		return ".ClassPlugin .element {border: 1px dotted #ccc; display: inline-block; padding: 1px; min-height: 1.5em; background-color: whitesmoke;}"
				+ ".ClassPlugin .name, .ClassPlugin .field {font-weight: bold;}"
				+ ".ClassPlugin .arguments {font-style: italic;}"
				+ ".ClassPlugin .element .returnType {font-style: italic;}";
	}

}
