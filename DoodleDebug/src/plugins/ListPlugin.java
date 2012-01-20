package plugins;

import html_generator.Tag;

import java.util.HashSet;
import java.util.List;
import java.util.ListResourceBundle;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Provider;

import rendering.ArrayRendering;
import rendering.ListRendering;

import view.DoodleCanvas;

import doodle.RealScratch;


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


}
