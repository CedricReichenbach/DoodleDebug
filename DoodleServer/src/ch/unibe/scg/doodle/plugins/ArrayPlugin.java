package ch.unibe.scg.doodle.plugins;

import java.lang.reflect.Array;
import java.util.HashSet;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Provider;

import ch.unibe.scg.doodle.htmlgen.Tag;
import ch.unibe.scg.doodle.rendering.ArrayRendering;
import ch.unibe.scg.doodle.rendering.DoodleRenderException;

public class ArrayPlugin extends AbstractPlugin {

	@Inject
	Provider<ArrayRendering> arrayRenderingProvider;

	@Override
	public Set<Class<?>> getDrawableClasses() {
		HashSet<Class<?>> hs = new HashSet<Class<?>>();
		hs.add(Array.class);
		return hs;
	}

	@Override
	public void render(Object array, Tag tag) {
		arrayRenderingProvider.get().render((Object[]) array, tag);
	}

	@Override
	public void renderSmall(Object array, Tag tag) {
		arrayRenderingProvider.get().renderSmall((Object[]) array, tag);
	}

	@Override
	public String getObjectTypeName(Object o) {
		if (ArrayRendering.checkIfElementsSameType((Object[]) o))
			// TODO: maybe smarter text
			return super.getObjectTypeName(o) + " (only "
					+ ((Object[]) o)[0].getClass().getSimpleName()
					+ " objects)";
		return super.getObjectTypeName(o);
	}

	@Override
	public String getCSS() {
		return ".ArrayPlugin .arrayElement "
				+ "{float:left;} "
				+ ".ArrayPlugin.smallRendering .arrayElement "
				+ "{float:left; background-color:black; height: 4px; width:4px; margin: 0 1px;}"; // XXX
	}

}
