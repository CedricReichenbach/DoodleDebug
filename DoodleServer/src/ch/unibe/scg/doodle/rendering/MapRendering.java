package ch.unibe.scg.doodle.rendering;

import java.util.Collection;
import java.util.Iterator;
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
		boolean sameKeyTypes = sameTypes(map.keySet());
		boolean sameValueTypes = sameTypes(map.values());
		Set<?> keySet = map.keySet();
		Tag table = new Tag("table", "cellpadding=0");
		table.addAttribute("cellspacing", "0");
		Tag tbody = new Tag("tbody");
		for (Object key : keySet) {
			Tag mapping = new Tag("tr", "class=mapping");
			Tag mapKey = new Tag("td", "class=mappingKey");
			if (sameKeyTypes)
				doodler.renderInlineIntoWithoutClassName(key, mapKey);
			else
				doodler.renderInlineInto(key, mapKey);
			mapping.add(mapKey);
			Tag arrow = new Tag("td", "class=mappingArrow");
			arrow.add("&rarr;");
			mapping.add(arrow);
			Tag mapValue = new Tag("td", "class=mappingValue");
			if (sameValueTypes)
				doodler.renderInlineIntoWithoutClassName(map.get(key), mapValue);
			else
				doodler.renderInlineInto(map.get(key), mapValue);
			mapping.add(mapValue);
			tbody.add(mapping);
		}
		table.add(tbody);
		tag.add(table);
	}

	public boolean sameTypes(Collection<?> objects) {
		if (objects.isEmpty())
			return true;
		try {
			Iterator<?> iterator = objects.iterator();
			final Object first = iterator.next().getClass();
			while (iterator.hasNext()) {
				if (!iterator.next().getClass().equals(first))
					return false;
			}
			return true;
		} catch (NullPointerException e) {
			return false;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void renderSmall(Map<?, ?> map, Tag tag) {
		tag.add("Map (" + map.size() + " mappings)");
	}
}
