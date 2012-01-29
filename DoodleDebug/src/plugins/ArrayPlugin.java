package plugins;

import html_generator.Tag;

import java.lang.reflect.Array;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Provider;

import rendering.ArrayRendering;
import rendering.ListRendering;

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
	public String getObjectTypeName(Object o) {
		if (ArrayRendering.checkIfElementsSameType((Object[]) o))
			// TODO: maybe smarter text
			return super.getObjectTypeName(o) + " (only "
					+ ((Object[]) o)[0].getClass().getSimpleName()
					+ " objects)";
		return super.getObjectTypeName(o);
	}

}
