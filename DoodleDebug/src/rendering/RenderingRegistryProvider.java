package rendering;

import java.util.HashMap;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Provider;

import plugins.Plugin;

public class RenderingRegistryProvider implements Provider<RenderingRegistry> {

	@Inject
	Set<Plugin> allPlugins;

	@Override
	public RenderingRegistry get() {
		HashMap<Class<?>, Plugin> m = new HashMap<Class<?>, Plugin>();
		for (Plugin p : allPlugins) {
			for (Class<?> type : p.getDrawableClasses()) {
				m.put(type, p);
			}
		}
		RenderingRegistry registry = new RenderingRegistry(m);

		return registry;
	}

}
