package ch.unibe.scg.doodle.plugins;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Provider;

import ch.unibe.scg.doodle.htmlgen.Tag;
import ch.unibe.scg.doodle.rendering.DoodleRenderException;

/**
 * A proxy for ArrayPlugin, they use the same rendering.
 * 
 * @author Cedric Reichenbach
 * 
 */
public class CollectionPlugin extends AbstractPlugin {

	@Inject
	Provider<ArrayPlugin> arrayPluginProvider;

	@Inject
	Provider<TablePlugin> tablePluginProvider;

	@Override
	public Set<Class<?>> getDrawableClasses() {
		HashSet<Class<?>> hs = new HashSet<Class<?>>();
		hs.add(Collection.class);
		return hs;
	}

	@Override
	public void render(Object collection, Tag tag) throws DoodleRenderException {
		if (twoDimCollection((Collection<?>) collection)) {
			tablePluginProvider.get().render(collection, tag);
			return;
		}
		Object[] array = ((Collection<?>) collection).toArray();
		arrayPluginProvider.get().render(array, tag);
	}

	@Override
	public void renderSmall(Object collection, Tag tag)
			throws DoodleRenderException {
		if (twoDimCollection((Collection<?>) collection)) {
			tablePluginProvider.get().renderSmall(collection, tag);
			return;
		}
		Object[] array = ((Collection<?>) collection).toArray();
		arrayPluginProvider.get().renderSmall(array, tag);
	}

	public String getObjectTypeName(Object collection) {
		if (checkIfElementsSameType((Collection<?>) collection)
				&& !((Collection<?>) collection).isEmpty())
			return super.getObjectTypeName(collection)
					+ " of "
					+ ((Collection<?>) collection).iterator().next().getClass()
							.getSimpleName();
		return super.getObjectTypeName(collection);
	}

	@Override
	public String getCSS() {
		return arrayPluginProvider.get().getCSS()
				+ tablePluginProvider.get().getCSS();
		// return ".ListPlugin .listElement "
		// + "{float:left;}"
		// + ".ListPlugin.smallRendering .listElement "
		// +
		// "{float:left; background-color:black; height: 4px; width:4px;} margin: 0 1px;";
		// // XXX
	}

	private boolean twoDimCollection(Collection<?> collection) {
		if (collection.isEmpty())
			return false;
		if (collection.iterator().next() instanceof Collection)
			return true;
		return false;
	}

	public static boolean checkIfElementsSameType(Collection<?> collection) {
		if (collection.isEmpty())
			return true;

		Class<?> first = collection.iterator().next().getClass();

		for (Object o : collection) {
			if (o == null || !(o.getClass().equals(first)))
				return false;
		}
		return true;
	}
}
