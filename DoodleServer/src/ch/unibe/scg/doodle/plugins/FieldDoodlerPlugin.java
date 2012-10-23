package ch.unibe.scg.doodle.plugins;

import java.util.HashSet;
import java.util.Set;

import javax.inject.Inject;

import ch.unibe.scg.doodle.api.FieldDoodler;
import ch.unibe.scg.doodle.htmlgen.Tag;
import ch.unibe.scg.doodle.rendering.DoodleRenderException;
import ch.unibe.scg.doodle.rendering.FieldDoodlerRendering;
import ch.unibe.scg.doodle.server.util.EclipseIconUtil;

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
	public void render(Object object, Tag tag) throws DoodleRenderException {
		fieldDoodlerRendering.render(object, tag);
	}

	@Override
	public void renderSimplified(Object object, Tag tag) {
		fieldDoodlerRendering.renderSimplified(object, tag);
	}

	@Override
	public String getCSS() {
		return titleCSS() + fieldCSS() + scopeCSS("FieldDoodlerPlugin")
				+ smallCSS();
	}

	private String titleCSS() {
		return ".FieldDoodlerPlugin .title {font-size: 12pt; margin: 4px;}";
	}

	private String fieldCSS() {
		return ".FieldDoodlerPlugin .fieldWrapper {margin: 3px 0;}"
				+ ".FieldDoodlerPlugin .field {border: 1px dotted #ccc; display: inline-block; padding: 1px; min-height: 1.5em; background-color: whitesmoke;}"
				+ ".FieldDoodlerPlugin .content {margin-left: 0.5em;}"
				+ ".FieldDoodlerPlugin .content, .FieldDoodlerPlugin .scope, .FieldDoodlerPlugin .name"
				+ "{display: inline-block;}"
				+ ".FieldDoodlerPlugin .name {float: left; margin: 1px 2px; font-weight: 500;}";
	}

	static String scopeCSS(String pluginClass) {
		return "."
				+ pluginClass
				+ " .scope "
				+ "{float: left; margin-left: 1px; height: 1em; width: 1em; position: relative; right: -1px;"
				+ "border-width: 1px 0 1px 1px; border-style: solid; border-color: #DDD; padding: 2px;"
				+ "background-color: whitesmoke; background-position: center; background-repeat: no-repeat;}"
				+"."
				+ pluginClass
				+" .scope.field"
				+"{border-style: dotted;}"
				+"."
				+ pluginClass
				+" .scope.method"
				+"{border-style: dashed;}"
				+ "."
				+ pluginClass
				+ " .scope.field.public {background-image: url('"
				+ EclipseIconUtil.getIcon(EclipseIconUtil.PUBLIC_FIELD).toURI()
				+ "')}"
				+ "."
				+ pluginClass
				+ " .scope.method.public {background-image: url('"
				+ EclipseIconUtil.getIcon(EclipseIconUtil.PUBLIC_METHOD)
						.toURI()
				+ "')}"
				+ "."
				+ pluginClass
				+ " .scope.protected {}"
				+ "."
				+ pluginClass
				+ " .scope.private {}"
				+ "."
				+ pluginClass
				+ " .scope.default {}"
				+ "."
				+ pluginClass
				+ " .scope p {position: relative; bottom: 0.75em; margin-right: 1px;}";
	}

	private String smallCSS() {
		return ".FieldDoodlerPlugin.smallRendering .scope {height: 1em; width: 1em; border-width: 1px; box-shadow: none}"
				+ ".FieldDoodlerPlugin.smallRendering .scope p {bottom: 1.1em;}";
	}

}
