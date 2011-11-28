package rendering;

import java.util.HashMap;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Provider;

import plugins.RenderingPlugin;

public class RenderingRegistryProvider implements Provider<RenderingRegistry> {

	@Inject
	Set<RenderingPlugin> allPlugins;

	@Override
	public RenderingRegistry get() {
		HashMap<Class<?>, RenderingPlugin> m = new HashMap<Class<?>, RenderingPlugin>();
		for (RenderingPlugin p : allPlugins) {
			assert p != null;
			for (Class<?> type : p.getDrawableClasses()) {
				m.put(type, p);
			}
		}
		RenderingRegistry registry = new RenderingRegistry(m);

		return registry;
	}

}
