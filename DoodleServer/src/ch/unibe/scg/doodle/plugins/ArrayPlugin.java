package ch.unibe.scg.doodle.plugins;

import java.lang.reflect.Array;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Provider;

import ch.unibe.scg.doodle.htmlgen.Tag;
import ch.unibe.scg.doodle.rendering.CollectionRendering;
import ch.unibe.scg.doodle.rendering.DoodleRenderException;

import static ch.unibe.scg.doodle.util.ArrayUtil.castToArray;

public class ArrayPlugin extends AbstractPlugin {

	@Inject
	CollectionPlugin collectionPlugin;

	@Inject
	Provider<CollectionRendering> collectionRenderingProvider;

	@Override
	public Set<Class<?>> getDrawableClasses() {
		HashSet<Class<?>> hs = new HashSet<Class<?>>();
		hs.add(Array.class);
		return hs;
	}

	@Override
	public void render(Object array, Tag tag) throws DoodleRenderException {
		collectionPlugin.render(Arrays.asList(array), tag);
	}

	@Override
	public void renderSmall(Object array, Tag tag) throws DoodleRenderException {
		collectionPlugin.renderSmall(Arrays.asList(array), tag);
	}

	@Override
	public String getObjectTypeName(Object o) {
		if (CollectionRendering.checkIfElementsSameType(Arrays.asList(o))
				&& Array.getLength(o) != 0)
			// TODO: maybe smarter text
			return super.getObjectTypeName(o) + " (only "
					+ (castToArray(o))[0].getClass().getSimpleName()
					+ " objects)";
		return super.getObjectTypeName(o);
	}

	@Override
	public String getClassAttribute() {
		return collectionPlugin.getClassAttribute();
	}

	@Override
	public String getCSS() {
		return collectionPlugin.getCSS();
		// return ".ArrayPlugin .arrayElement "
		// + "{float:left;} "
		// + ".ArrayPlugin.smallRendering .arrayElement "
		// +
		// "{float:left; background-color:black; height: 4px; width:4px; margin: 0 1px;}";
		// // XXX
	}

}
