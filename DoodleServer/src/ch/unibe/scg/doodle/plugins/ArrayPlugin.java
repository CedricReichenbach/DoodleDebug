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
import static ch.unibe.scg.doodle.util.ArrayUtil.asList;

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
		collectionPlugin.render(asList(array), tag);
	}

	@Override
	public void renderSmall(Object array, Tag tag) throws DoodleRenderException {
		collectionPlugin.renderSmall(asList(array), tag);
	}

	@Override
	public String getObjectTypeName(Object o) {
		if (CollectionRendering.checkIfElementsSameType(asList(o))
				&& Array.getLength(o) != 0)
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
	}

}
