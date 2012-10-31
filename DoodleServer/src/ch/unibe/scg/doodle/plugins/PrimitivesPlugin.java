package ch.unibe.scg.doodle.plugins;

import java.util.HashSet;
import java.util.Set;

import javax.inject.Inject;

import ch.unibe.scg.doodle.htmlgen.Tag;
import ch.unibe.scg.doodle.rendering.DoodleRenderException;
import ch.unibe.scg.doodle.rendering.EnumRendering;

public class PrimitivesPlugin extends AbstractPlugin {

	@Inject
	StringPlugin stringPlugin;

	@Inject
	EnumRendering enumRendering;

	@Override
	public Set<Class<?>> getDrawableClasses() {
		Set<Class<?>> hs = new HashSet<Class<?>>();
		hs.add(Enum.class);
		hs.add(Character.class);
		return hs;
	}

	@Override
	public void render(Object object, Tag tag) throws DoodleRenderException {
		if (object instanceof Character)
			stringPlugin.render(object.toString(), tag);
		else if (object instanceof Enum) {
			enumRendering.render((Enum<?>) object, tag);
		} else {
			throw new DoodleRenderException(
					"Accidentially attempted to render object as char/enum");
		}
	}

	@Override
	public void renderSimplified(Object object, Tag tag)
			throws DoodleRenderException {
		if (object instanceof Character)
			stringPlugin.renderSimplified(object.toString(), tag);
		else if (object instanceof Enum) {
			enumRendering.renderSimplified((Enum<?>) object, tag);
		} else {
			throw new DoodleRenderException(
					"Accidentially attempted to render object as char/enum");
		}
	}

	@Override
	public String getObjectTypeName(Object o) {
		if (o instanceof Enum) {
			return super.getObjectTypeName(o) + " (Enum)";
		}
		return super.getObjectTypeName(o);
	}

	@Override
	public String getClassAttribute() {
		return stringPlugin.getClassAttribute() + " "
				+ super.getClassAttribute();
	}

	@Override
	public String getCSS() {
		return stringPlugin.getCSS() + enumCSS();
	}

	private String enumCSS() {
		return ".PrimitivesPlugin .enumConstant {color: rgba(0,0,0,0.5); display: inline-block;}"
				+ ".PrimitivesPlugin .enumConstant.active {color: black; font-weight: 500;}";
	}
}
