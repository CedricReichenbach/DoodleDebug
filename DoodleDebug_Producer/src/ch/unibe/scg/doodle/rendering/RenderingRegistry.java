package ch.unibe.scg.doodle.rendering;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import ch.unibe.scg.doodle.api.FieldDoodler;
import ch.unibe.scg.doodle.hbase.ClassManager;
import ch.unibe.scg.doodle.hbase.CloudObject;
import ch.unibe.scg.doodle.plugins.ArrayPlugin;
import ch.unibe.scg.doodle.plugins.FieldDoodlerPlugin;
import ch.unibe.scg.doodle.plugins.PrimitivesPlugin;
import ch.unibe.scg.doodle.plugins.RenderingPlugin;
import ch.unibe.scg.doodle.plugins.TablePlugin;

/**
 * Manages different Renderings, can be organized by user (e.g. different
 * renderings for specific objects and bring in their own renderings)
 * 
 * @author Cedric Reichenbach
 * 
 */
public class RenderingRegistry {

	private final ArrayPlugin arrayPlugin;

	private final TablePlugin tablePlugin;

	private FieldDoodlerPlugin fieldDoodlerPlugin;

	private PrimitivesPlugin primitivesPlugin;

	private final HashMap<Class<?>, RenderingPlugin> map;

	private static RenderingRegistry instance;

	private static final String PLUGIN_CONTAINER_ID = "userPluginCollection";
	private static CloudObject<Collection<RenderingPlugin>> userPluginsContainer;

	public RenderingRegistry(HashMap<Class<?>, RenderingPlugin> m,
			ArrayPlugin arrayPlugin, TablePlugin tablePlugin,
			FieldDoodlerPlugin fieldDoodlerPlugin,
			PrimitivesPlugin primitivesPlugin) {
		this.map = m;
		assert map.containsKey(Object.class);
		this.arrayPlugin = arrayPlugin;
		this.tablePlugin = tablePlugin;
		this.fieldDoodlerPlugin = fieldDoodlerPlugin;
		this.primitivesPlugin = primitivesPlugin;

		if (userPluginsContainer == null)
			userPluginsContainer = new CloudObject<Collection<RenderingPlugin>>(
					new ArrayList<RenderingPlugin>(), PLUGIN_CONTAINER_ID);
		this.map.putAll(RenderingRegistryProvider
				.mapFromPlugins(userPluginsContainer.get()));

		instance = this;
	}

	/**
	 * Construct a list of lists of all supertypes (superclasses and
	 * superinterfaces). They're organized levelwise: close supertypes come
	 * first, distant ones later. For example, the first level of Integer is:
	 * Number, Comparable. The second level is Serializable, Object.
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
		if (isFieldDoodler(type)) {
			return fieldDoodlerPlugin;
		}

		if (type.isArray()) {
			if (type.getComponentType().isArray()) {
				return tablePlugin;
			}
			return arrayPlugin;
		}

		if (isPrimitive(type)) {
			return primitivesPlugin;
		}

		List<List<Class<?>>> levels = superTypesLevelwise(type);

		for (List<Class<?>> level : levels) {
			for (Class<?> curType : level) {
				RenderingPlugin plugin = map.get(curType);
				if (plugin != null) {
					// there could still be super interfaces
					if (!curType.equals(Object.class)) {
						return plugin;
					}
				}
			}
		}
		RenderingPlugin plugin = map.get(Object.class);
		if (plugin != null)
			return plugin;
		throw new AssertionError();

	}

	private boolean isPrimitive(Class<?> type) {
		for (Class<?> c : primitivesPlugin.getDrawableClasses()) {
			if (c.isAssignableFrom(type))
				return true;
		}
		return false;
	}

	private boolean isFieldDoodler(Class<?> type) {
		List<Class<?>> interfaces = Arrays.asList(type.getInterfaces());
		return interfaces.contains(FieldDoodler.class);
	}

	public static void addPlugins(Collection<RenderingPlugin> plugins) {
		for (RenderingPlugin plugin : plugins)
			ClassManager.store(plugin.getClass());
		if (userPluginsContainer == null)
			userPluginsContainer = new CloudObject<Collection<RenderingPlugin>>(
					plugins, PLUGIN_CONTAINER_ID);
		else {
			userPluginsContainer.get().addAll(plugins);
			userPluginsContainer.save();
		}

		if (instance != null) { // when call in the middle of a run
			instance.map.putAll(RenderingRegistryProvider
					.mapFromPlugins(plugins));
		}
	}

	public static void clearUserPlugins() {
		userPluginsContainer = null;
	}
}
