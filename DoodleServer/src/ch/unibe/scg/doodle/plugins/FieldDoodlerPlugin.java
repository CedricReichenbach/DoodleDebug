package ch.unibe.scg.doodle.plugins;

import java.util.HashSet;
import java.util.Set;

import javax.inject.Inject;

import ch.unibe.scg.doodle.api.FieldDoodler;
import ch.unibe.scg.doodle.htmlgen.Tag;
import ch.unibe.scg.doodle.rendering.DoodleRenderException;
import ch.unibe.scg.doodle.rendering.FieldDoodlerRendering;

/**
 * Plugin to print all fields of a class.
 * 
 * @author Cedric Reichenbach
 * 
 */
public class FieldDoodlerPlugin extends AbstractPlugin {

	@Inject
	FieldDoodlerRendering fieldDoodlerRendering;

	@Override
	public Set<Class<?>> getDrawableClasses() {
		HashSet<Class<?>> hs = new HashSet<Class<?>>();
		hs.add(FieldDoodler.class);
		return hs;
	}

	@Override
	public void render(Object fieldDoodler, Tag tag)
			throws DoodleRenderException {
		fieldDoodlerRendering.render((FieldDoodler) fieldDoodler, tag);
	}

	@Override
	public void renderSimplified(Object fieldDoodler, Tag tag) {
		fieldDoodlerRendering
				.renderSimplified((FieldDoodler) fieldDoodler, tag);
	}

	@Override
	public String getCSS() {
		return titleCSS() + fieldCSS() + scopeCSS();
	}

	private String titleCSS() {
		return ".FieldDoodlerPlugin .title {font-size: 12pt; margin: 4px;}";
	}

	private String fieldCSS() {
		return ".FieldDoodlerPlugin .fieldWrapper {margin: 4px 0;}"
				+ ".FieldDoodlerPlugin .field {border: 1px dotted #ccc; display: inline-block; padding: 2px; min-height: 2em; background-color: whitesmoke;}"
				+ ".FieldDoodlerPlugin .content {margin-left: 0.5em;}"
				+ ".FieldDoodlerPlugin .content, .FieldDoodlerPlugin .scope, .FieldDoodlerPlugin .name"
				+ "{display: inline-block;}"
				+ ".FieldDoodlerPlugin .name {float: left; margin: 2px;}";
	}

	private String scopeCSS() {
		return ".FieldDoodlerPlugin .scope "
				+ "{float: left; margin-left: 2px; height: 2em; width: 2em; text-align: center;"
				+ "border-width: 1px 0 1px 1px; border-style: solid; border-color: #DDD;"
				+ "box-shadow: -9px 0 12px -10px rgba(0,0,0,0.5) inset;}"
				+ ".FieldDoodlerPlugin .scope.public {background-color: #8f8; border-color: #6d6;}"
				+ ".FieldDoodlerPlugin .scope.protected {background-color: #ff4; border-color: #dd0;}"
				+ ".FieldDoodlerPlugin .scope.private {background-color: #fcc; border-color: #d99;}"
				+ ".FieldDoodlerPlugin .scope.default {background-color: #0df; border-color: #0bd;}" +
				".FieldDoodlerPlugin .static .scope {border-style: double; border-color: #888;}" +
				".FieldDoodlerPlugin .static .name {text-decoration: underline;}" +
				".FieldDoodlerPlugin .static .content {color: #666; font-style: italic;}"
				+ ".FieldDoodlerPlugin .scope p {position: relative; top: -0.5em;}";
	}

}
