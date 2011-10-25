package rendering;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;

import plugins.Plugin;

/**
 * Manages different Renderings, can be organized by user (e.g. different renderings for specific objects and bring in their own renderings)
 * @author Cedric Reichenbach
 *
 */
public class RenderingRegistry {
	
	
	private final HashMap<Class<?>, Plugin> map;

	public RenderingRegistry(HashMap<Class<?>, Plugin> m) {
		this.map = m;
		assert map.containsKey(Object.class);
	}

	public Plugin lookup(Class<?> type) {
		Plugin plugin = map.get(type);
		
		if (plugin == null) {
			return this.lookup(type.getSuperclass());
		}
		
		return plugin;
		
	}

}
