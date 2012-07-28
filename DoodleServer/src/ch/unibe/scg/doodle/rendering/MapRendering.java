package ch.unibe.scg.doodle.rendering;

import java.util.Map;
import java.util.Set;

import javax.inject.Inject;

import ch.unibe.scg.doodle.Doodler;
import ch.unibe.scg.doodle.htmlgen.Tag;

public class MapRendering implements Rendering<Map<?, ?>> {

	@Inject
	Doodler doodler;

	@Override
	public void render(Map<?, ?> map, Tag tag) {
		Set<?> keySet = map.keySet();
		for (Object key : keySet) {
			Tag mapping = new Tag("div", "class=mapping");
			Tag mapKey = new Tag("div", "class=mappingKey");
			doodler.renderInlineInto(key, mapKey);
			mapping.add(mapKey);
			Tag arrow = new Tag("div", "class=mappingArrow");
			arrow.add("&rarr;");
			mapping.add(arrow);
			Tag mapValue = new Tag("div", "class=mappingValue");
			doodler.renderInlineInto(map.get(key), mapValue);
			mapping.add(mapValue);
			tag.add(mapping);
		}
	}

	@Override
	public void renderSmall(Map<?, ?> map, Tag tag) {
		tag.add("Map (" + map.size() + " mappings)");
	}
}
