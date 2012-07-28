package ch.unibe.scg.doodle.plugins;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
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
	public void render(Object array, Tag tag) throws DoodleRenderException {
		arrayRenderingProvider.get().render(castToArray(array), tag);
	}

	protected Object[] castToArray(Object array) {
		try {
			return (Object[]) array;
		} catch (ClassCastException e) { // was array of primitives
			return castPrimitivesArray(array);
		}
	}

	/**
	 * Prevent error for arrays of primitive data types. There seems to be no
	 * easier way.
	 * 
	 * @param array
	 * @return
	 */
	private Object[] castPrimitivesArray(Object array) {
		int length = Array.getLength(array);
		Object[] casted = new Object[length];
		for (int i = 0; i < length; i++) {
			casted[i] = Array.get(array, i);
		}
		return casted;
	}

	@Override
	public void renderSmall(Object array, Tag tag) throws DoodleRenderException {
		arrayRenderingProvider.get().renderSmall(castToArray(array), tag);
	}

	@Override
	public String getObjectTypeName(Object o) {
		if (ArrayRendering.checkIfElementsSameType(castToArray(o)))
			// TODO: maybe smarter text
			return super.getObjectTypeName(o) + " (only "
					+ (castToArray(o))[0].getClass().getSimpleName()
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
