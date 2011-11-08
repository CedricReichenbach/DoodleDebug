package rendering;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;

import plugins.RenderingPlugin;

/**
 * Manages different Renderings, can be organized by user (e.g. different renderings for specific objects and bring in their own renderings)
 * @author Cedric Reichenbach
 *
 */
public class RenderingRegistry {
	
	
	private final HashMap<Class<?>, RenderingPlugin> map;

	public RenderingRegistry(HashMap<Class<?>, RenderingPlugin> m) {
		this.map = m;
		assert map.containsKey(Object.class);
	}

	public RenderingPlugin lookup(Class<?> type) {
		RenderingPlugin renderingPlugin = map.get(type);
		
		if (renderingPlugin == null) {
			return this.lookup(type.getSuperclass());
		}
		
		return renderingPlugin;
		
	}

}
