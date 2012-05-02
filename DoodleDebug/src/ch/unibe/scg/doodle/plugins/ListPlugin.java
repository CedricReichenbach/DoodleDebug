package ch.unibe.scg.doodle.plugins;


import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Provider;

import ch.unibe.ch.scg.htmlgen.Tag;
import ch.unibe.scg.doodle.rendering.ListRendering;



public class ListPlugin extends AbstractPlugin {

	@Inject
	Provider<ListRendering> listRenderingProvider;
	
	@Override
	public Set<Class<?>> getDrawableClasses() {
		HashSet<Class<?>> hs = new HashSet<Class<?>>();
		hs.add(List.class);
		return hs;
	}

	@Override
	public void render(Object list, Tag tag) {
		listRenderingProvider.get().render((List) list, tag);
	}

	public String getObjectTypeName(Object o) {
		if (ListRendering.checkIfElementsSameType((List)o))
			return super.getObjectTypeName(o) + " of "+((List)o).get(0).getClass().getSimpleName();
		return super.getObjectTypeName(o);
	}


	@Override
	public String getCSS() {
		return ".ListPlugin .listElement {float:left;}"; // XXX
	}

}
