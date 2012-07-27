package ch.unibe.scg.doodle.plugins;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Provider;

import ch.unibe.scg.doodle.htmlgen.Tag;
import ch.unibe.scg.doodle.rendering.ArrayRendering;
import ch.unibe.scg.doodle.rendering.DoodleRenderException;
import ch.unibe.scg.doodle.rendering.TwoDimArrayRendering;

/**
 * Plugin responsible for rendering of two-dimensional data structures like
 * tables, matrices and 2-dimensional arrays.
 * 
 * @author Cedric Reichenbach
 * 
 */
public class TablePlugin extends ArrayPlugin {

	@Inject
	Provider<TwoDimArrayRendering> twoDimArrayRenderingProvider;

	@Override
	public Set<Class<?>> getDrawableClasses() {
		HashSet<Class<?>> hs = new HashSet<Class<?>>();
		return hs;
	}

	@Override
	public void render(Object table, Tag tag) {
		if (table.getClass().isArray()) {
			twoDimArrayRenderingProvider.get()
					.render(castTo2DimArr(table), tag);
		}
	}

	private Object[][] castTo2DimArr(Object twoDimArray) {
		Object[][] result = new Object[Array.getLength(twoDimArray)][];
		Object[] arrays = castToArray(twoDimArray);
		for (int i = 0; i < arrays.length; i++) {
			result[i] = castToArray(arrays[i]);
		}
		return result;
	}

	@Override
	public void renderSmall(Object table, Tag tag) {
		// TODO Auto-generated method stub
		tag.add("table here");
	}
	
	@Override
	public String getCSS() {
		return ".TablePlugin td {float:none;}";
	}

}
