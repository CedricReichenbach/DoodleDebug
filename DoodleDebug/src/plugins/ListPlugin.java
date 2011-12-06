package plugins;

import html_generator.Tag;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import view.DoodleCanvas;

import doodle.RealScratch;


public class ListPlugin extends AbstractPlugin {

	@Override
	public Set<Class<?>> getDrawableClasses() {
		HashSet<Class<?>> hs = new HashSet<Class<?>>();
		hs.add(List.class);
		return hs;
	}

	@Override
	public void render(Object object, Tag tag) {
		// TODO Auto-generated method stub
		
	}


}
