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
		Tag table = new Tag("table", "cellpadding=0");
		table.addAttribute("cellspacing", "0");
		Tag tbody = new Tag("tbody");
		for (Object key : keySet) {
			Tag mapping = new Tag("tr", "class=mapping");
			Tag mapKey = new Tag("td", "class=mappingKey");
			doodler.renderInlineInto(key, mapKey);
			mapping.add(mapKey);
			Tag arrow = new Tag("td", "class=mappingArrow");
			arrow.add("&rarr;");
			mapping.add(arrow);
			Tag mapValue = new Tag("td", "class=mappingValue");
			doodler.renderInlineInto(map.get(key), mapValue);
			mapping.add(mapValue);
			tbody.add(mapping);
		}
		table.add(tbody);
		tag.add(table);
	}

	@Override
	public void renderSmall(Map<?, ?> map, Tag tag) {
		tag.add("Map (" + map.size() + " mappings)");
	}
}
