package ch.unibe.scg.doodle.plugins;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Provider;

import ch.unibe.scg.doodle.htmlgen.Tag;
import ch.unibe.scg.doodle.rendering.CollectionRendering;
import ch.unibe.scg.doodle.rendering.DoodleRenderException;

/**
 * A proxy for ArrayPlugin, they use the same rendering.
 * 
 * @author Cedric Reichenbach
 * 
 */
public class CollectionPlugin extends AbstractPlugin {

	@Inject
	Provider<TablePlugin> tablePluginProvider;

	@Inject
	CollectionRendering collectionRendering;

	private static Map<Integer, Collection<?>> iteratorListMap = new HashMap<Integer, Collection<?>>();

	@Override
	public Set<Class<?>> getDrawableClasses() {
		HashSet<Class<?>> hs = new HashSet<Class<?>>();
		hs.add(Collection.class);
		hs.add(Iterator.class);
		return hs;
	}

	@Override
	public void render(Object collection, Tag tag) throws DoodleRenderException {
		if (collection instanceof Iterator) {
			collection = toCollection((Iterator<?>) collection);
		}

		if (twoDimCollection((Collection<?>) collection)) {
			tablePluginProvider.get().render(collection, tag);
			return;
		}

		collectionRendering.render((Collection<?>) collection, tag);
	}

	@Override
	public void renderSimplified(Object collection, Tag tag)
			throws DoodleRenderException {
		if (collection instanceof Iterator) {
			collection = toCollection((Iterator<?>) collection);
		}

		if (twoDimCollection((Collection<?>) collection)) {
			tablePluginProvider.get().renderSimplified(collection, tag);
			return;
		}

		collectionRendering.renderSimplified((Collection<?>) collection, tag);
	}

	@Override
	public String getObjectTypeName(Object collection) {
		Iterator<?> iterator = null;
		if (collection instanceof Iterator) {
			iterator = (Iterator<?>) collection;
			collection = toCollection((Iterator<?>) collection);
		}

		if (checkIfElementsSameType((Collection<?>) collection)
				&& !((Collection<?>) collection).isEmpty())
			return (iterator != null ? iterator : collection).getClass()
					.getSimpleName()
					+ " ("
					+ ((Collection<?>) collection).iterator().next().getClass()
							.getSimpleName() + " elements)";
		return super.getObjectTypeName(collection);
	}

	@Override
	public String getCSS() {
		String element = ".CollectionPlugin .collectionElement {float:left; border-right: 1px solid #ddd}";
		String lastElement = ".CollectionPlugin .collectionElement:last-child {border: none;}";
		String smallElement = ".CollectionPlugin.smallRendering .collectionElement "
				+ "{float:left; background-color:black; height: 6px; width:6px; margin: 0 1px;}";
		String dot = ".CollectionPlugin.smallRendering .collectionDot "
				+ "{height: 2px; width: 2px; margin-top: 4px; margin-right: 0;}";
		return element + lastElement + smallElement + dot
				+ tablePluginProvider.get().getCSS();
	}

	private Collection<?> toCollection(Iterator<?> iterator) {
		// XXX: nasty, but no other solution available in my head
		if (iteratorListMap.containsKey(System.identityHashCode(iterator))) {
			return iteratorListMap.get(System.identityHashCode(iterator));
		}

		Collection<Object> list = new LinkedList<Object>();
		while (iterator.hasNext()) {
			System.out.println(System.identityHashCode(iterator));
			list.add(iterator.next());
		}

		// store iterator list
		iteratorListMap.put(System.identityHashCode(iterator), list);

		return list;
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

		try {
			Class<?> first = collection.iterator().next().getClass();

			for (Object o : collection) {
				if (o == null || !(o.getClass().equals(first)))
					return false;
			}
			return true;
		} catch (NullPointerException e) {
			return false;
		}
	}
}
