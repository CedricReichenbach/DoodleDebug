package ch.unibe.scg.doodle.plugins;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Provider;

import ch.unibe.scg.doodle.htmlgen.Tag;
import ch.unibe.scg.doodle.rendering.DoodleRenderException;
import ch.unibe.scg.doodle.rendering.MapRendering;

public class MapPlugin extends AbstractPlugin {

	@Inject
	MapRendering mapRendering;

	@Override
	public Set<Class<?>> getDrawableClasses() {
		HashSet<Class<?>> hs = new HashSet<Class<?>>();
		hs.add(Map.class);
		return hs;
	}

	@Override
	public void render(Object object, Tag tag) throws DoodleRenderException {
		mapRendering.render((Map<?, ?>) object, tag);
	}

	@Override
	public void renderSimplified(Object object, Tag tag)
			throws DoodleRenderException {
		mapRendering.renderSimplified((Map<?, ?>) object, tag);
	}

	@Override
	public String getCSS() {
		String all = ".MapPlugin .mappingKey, .MapPlugin .mappingArrow, .MapPlugin .mappingValue {float: none;}";
		String key = ".MapPlugin .mappingKey {text-align: right;}";
		String arrow = ".MapPlugin .mappingArrow {padding: 0 4px;}";
		String value = ".MapPlugin .mappingValue {}";
		String hr = ".MapPlugin .betweenMappings {}";
		return all + key + arrow + value + hr;
	}

	@Override
	public String getObjectTypeName(Object mapObject) {
		Map<?, ?> map = (Map<?, ?>) mapObject;
		if (!map.isEmpty())
			return super.getObjectTypeName(mapObject)
					+ ": "
					+ (mapRendering.sameTypes(map.keySet()) ? keyType(map)
							: "*")
					+ " &rarr; "
					+ (mapRendering.sameTypes(map.values()) ? valueType(map)
							: "*");
		return super.getObjectTypeName(mapObject);
	}

	private String keyType(Map<?, ?> map) {
		Set<?> keySet = map.keySet();
		if (keySet.isEmpty())
			return "(unknown)";
		Object first = keySet.iterator().next();
		return first.getClass().getSimpleName();
	}

	private String valueType(Map<?, ?> map) {
		Collection<?> values = map.values();
		if (values.isEmpty())
			return "(unknown)";
		Object first = values.iterator().next();
		return first.getClass().getSimpleName();
	}
}
