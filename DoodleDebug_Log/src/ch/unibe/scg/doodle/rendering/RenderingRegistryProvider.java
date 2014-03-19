package ch.unibe.scg.doodle.rendering;

import java.util.Collection;
import java.util.HashMap;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Provider;

import ch.unibe.scg.doodle.plugins.ArrayPlugin;
import ch.unibe.scg.doodle.plugins.FieldDoodlerPlugin;
import ch.unibe.scg.doodle.plugins.PrimitivesPlugin;
import ch.unibe.scg.doodle.plugins.RenderingPlugin;
import ch.unibe.scg.doodle.plugins.TablePlugin;

public class RenderingRegistryProvider implements Provider<RenderingRegistry> {

	@Inject
	Set<RenderingPlugin> allPlugins;

	@Inject
	Provider<ArrayPlugin> arrayPluginProvider;

	@Inject
	Provider<TablePlugin> tablePluginProvider;

	@Inject
	FieldDoodlerPlugin fieldDoodlerPlugin;

	@Inject
	PrimitivesPlugin primitivesPlugin;

	@Override
	public RenderingRegistry get() {
		HashMap<Class<?>, RenderingPlugin> m = mapFromPlugins(allPlugins);
		RenderingRegistry registry = new RenderingRegistry(m,
				arrayPluginProvider.get(), tablePluginProvider.get(),
				fieldDoodlerPlugin, primitivesPlugin);

		return registry;
	}

	public static HashMap<Class<?>, RenderingPlugin> mapFromPlugins(
			Collection<RenderingPlugin> plugins) {
		HashMap<Class<?>, RenderingPlugin> m = new HashMap<Class<?>, RenderingPlugin>();
		for (RenderingPlugin p : plugins) {
			assert p != null;
			for (Class<?> type : p.getDrawableClasses()) {
				m.put(type, p);
			}
		}
		return m;
	}

}
