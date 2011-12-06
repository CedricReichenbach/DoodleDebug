package rendering;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import plugins.ArrayPlugin;
import plugins.RenderingPlugin;

/**
 * Manages different Renderings, can be organized by user (e.g. different
 * renderings for specific objects and bring in their own renderings)
 * 
 * @author Cedric Reichenbach
 * 
 */
public class RenderingRegistry {

	private final HashMap<Class<?>, RenderingPlugin> map;

	public RenderingRegistry(HashMap<Class<?>, RenderingPlugin> m) {
		this.map = m;
		assert map.containsKey(Object.class);
	}

	/**
	 * Construct a list of lists of all supertypes (superclasses and superinterfaces). They're organized levelwise:
	 * close supertypes come first, distant ones later. For example, the first level of Integer is: Number, Comparable. The second level is Serializable, Object.
	 * 
	 * Used for iterating all supertypes levelwise.
	 * 
	 * @param type
	 * @return
	 */
	public static List<List<Class<?>>> superTypesLevelwise(Class<?> type) {
		List<List<Class<?>>> result = new ArrayList<List<Class<?>>>();

		List<Class<?>> current = new ArrayList<Class<?>>();
		current.add(type);

		while (!current.isEmpty()) {
			result.add(current);

			List<Class<?>> next = new ArrayList<Class<?>>();
			for (Class<?> c : current) {
				assert c != null;
				next.addAll(Arrays.asList(c.getInterfaces()));

				Class<?> superclass = c.getSuperclass();
				if (superclass != null) {
					next.add(superclass);
				}
			}

			current = next;
		}

		return result;
	}

	public RenderingPlugin lookup(Class<?> type) {
		if(type.isArray()) {
			return new ArrayPlugin();
		}
		

		List<List<Class<?>>> levels = superTypesLevelwise(type);
		
		for (List<Class<?>> level : levels){
			for (Class<?> curType : level) {
				RenderingPlugin plugin = map.get(curType);
				if (plugin != null) {
					return plugin;
				}
			}
		}
		throw new AssertionError();

	}

}
