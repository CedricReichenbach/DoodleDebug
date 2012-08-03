package ch.unibe.scg.doodle.plugins;

import java.util.HashSet;
import java.util.Set;

import javax.inject.Inject;

import ch.unibe.scg.doodle.Doodler;
import ch.unibe.scg.doodle.helperClasses.DoodleList;
import ch.unibe.scg.doodle.htmlgen.Tag;

public class DoodleListPlugin extends CollectionPlugin {

	@Inject
	Doodler doodler;

	@Override
	public Set<Class<?>> getDrawableClasses() {
		HashSet<Class<?>> hs = new HashSet<Class<?>>();
		hs.add(DoodleList.class);
		return hs;
	}

	@Override
	public void render(Object list, Tag tag) {
		DoodleList doodleList = (DoodleList) list;
		Tag table = new Tag("table", "class=doodleListTable", "cellpadding=0",
				"cellspacing=0");
		Tag tbody = new Tag("tbody");
		Tag tr = new Tag("tr");
		Tag paddingBefore = null;
		Tag delimiter = null;
		Tag paddingAfter = null;
		for (Object o : doodleList) {
			Tag element = new Tag("td", "class=collectionElement");
			doodler.renderInlineInto(o, element);
			tr.add(element);
			paddingBefore = new Tag("td", "class=paddingCell");
			tr.add(paddingBefore);
			delimiter = new Tag("td", "class=verticalDelimiter");
			delimiter.addCSSClass("rendering");
			tr.add(delimiter);
			paddingAfter = new Tag("td", "class=paddingCell");
			tr.add(paddingAfter);
		}
		if (paddingBefore != null && delimiter != null && paddingAfter != null) {
			paddingBefore.addAttribute("style", "display:none");
			delimiter.addAttribute("style", "display:none");
			paddingAfter.addAttribute("style", "display:none");
		}
		tbody.add(tr);
		table.add(tbody);
		tag.add(table);
	}

	@Override
	public String getObjectTypeName(Object o) {
		return "";
	}

	@Override
	public String getClassAttribute() {
		return CollectionPlugin.class.getSimpleName();
	}

	@Override
	public String getCSS() {
		return super.getCSS()
				+ ".CollectionPlugin .verticalDelimiter {padding: 0; width: 1px; background-color: #ddd;} " +
				".CollectionPlugin .doodleListTable td {vertical-align: top; float: none;} " +
				".CollectionPlugin .doodleListTable .paddingCell {padding: 1px;} ";
	}
}
