package plugins;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import view.DoodleCanvas;

import doodle.Scratch;


public class ListPlugin implements RenderingPlugin {

	@Override
	public Set<Class<?>> getDrawableClasses() {
		HashSet<Class<?>> hs = new HashSet<Class<?>>();
		hs.add(List.class);
		return hs;
	}

	@Override
	public void render(Object object, DoodleCanvas canvas) {
		// TODO Auto-generated method stub
		
	}


}
