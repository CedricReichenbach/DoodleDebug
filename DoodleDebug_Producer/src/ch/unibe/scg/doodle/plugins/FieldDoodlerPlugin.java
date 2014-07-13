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
		return ".FieldDoodlerPlugin .fieldWrapper {margin: 3px 0; white-space: nowrap;}"
				+ ".FieldDoodlerPlugin .field {border: 1px dotted #ccc; display: inline-block; padding: 1px; background-color: whitesmoke;}"
				+ ".FieldDoodlerPlugin .content {margin-left: 0.5em;}"
				+ ".FieldDoodlerPlugin .content, .FieldDoodlerPlugin .scope, .FieldDoodlerPlugin .name"
				+ "{display: inline-block;}"
				+ ".FieldDoodlerPlugin .name {float: left; margin: 1px 2px; font-weight: 500;}"
				+ ".FieldDoodlerPlugin .noFieldsMessage {color: rgba(0,0,0,0.5);}";
	}

	static String scopeCSS(String pluginClass) {
		return "."
				+ pluginClass
				+ " .scope "
				+ "{float: left; margin-left: 1px; height: 1em; width: 1em; position: relative; right: -1px;"
				+ "border-width: 1px 0 1px 1px; border-style: solid; border-color: #DDD; padding: 3px;"
				+ "background-color: whitesmoke; background-position: center; background-repeat: no-repeat;}"
				+ "."
				+ pluginClass
				+ " .scope.field"
				+ "{border-style: dotted;}"
				+ "."
				+ pluginClass
				+ " .scope.method"
				+ "{border-style: dashed;}"
				+ publicScopeCSS(pluginClass)
				+ protectedScopeCSS(pluginClass)
				+ privateScopeCSS(pluginClass)
				+ defaultScopeCSS(pluginClass)
				+ staticPropertyCSS(pluginClass)
				+ "."
				+ pluginClass
				+ " .scope p {position: relative; bottom: 0.75em; margin-right: 1px;}"
				+ " .scope:before {position:absolute; top:50%; left:50%;"
				+ "margin:-0.5em 0 0 -0.5em; font-size: 1em; line-height: 1em}";
	}

	private static String publicScopeCSS(String pluginClass) {
		return ".scope.public:before {color: green} ." + pluginClass
				+ " .scope.field.public:before {content:'○'} ." + pluginClass
				+ " .scope.method.public:before {content:'●'} ." + pluginClass
				+ " .scope.class.public:before {content:''}";
	}

	private static String protectedScopeCSS(String pluginClass) {
		return ".scope.protected:before {color:gold} ." + pluginClass
				+ " .scope.field.protected:before {content:'◇'}." + pluginClass
				+ " .scope.method.protected:before {content:'◆'}."
				+ pluginClass + " .scope.class.protected:before {content:''}";
	}

	private static String privateScopeCSS(String pluginClass) {
		return ".scope.private:before {color:#e04} ." + pluginClass
				+ " .scope.field.private:before {content:'□'} ." + pluginClass
				+ " .scope.method.private:before {content:'◼'} ." + pluginClass
				+ " .scope.class.private:before {content:''}";
	}

	private static String defaultScopeCSS(String pluginClass) {
		return ".scope.default:before {color:#04e} ." + pluginClass
				+ " .scope.field.default:before {content:'△'} ." + pluginClass
				+ " .scope.method.default:before {content:'▲'} ." + pluginClass
				+ " .scope.class.default:before {content:''}";
	}

	private static String staticPropertyCSS(String pluginClass) {
		return "." + pluginClass
				+ " .scope .static:before {content: 'S'; color: gray;"
				+ "font-size: 0.6em; position: absolute; right: 2px;"
				+ "text-shadow: 1px 1px 0 rgba(0, 0, 0, 0.2); top: 0;}";
	}

	private String smallCSS() {
		return ".FieldDoodlerPlugin.smallRendering .scope {height: 1em; width: 1em; border-width: 1px; box-shadow: none}"
				+ ".FieldDoodlerPlugin.smallRendering .scope p {bottom: 1.1em;}";
	}

}
