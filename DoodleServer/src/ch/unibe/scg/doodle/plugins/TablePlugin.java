package ch.unibe.scg.doodle.plugins;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
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
	public void render(Object table, Tag tag) throws DoodleRenderException {
		Object[][] arrays = convertTo2DArray(table);
		twoDimArrayRenderingProvider.get().render(arrays, tag);
	}

	@Override
	public void renderSmall(Object table, Tag tag) throws DoodleRenderException {
		Object[][] arrays = convertTo2DArray(table);
		int rows = arrays.length;
		tag.add("table (" + rows + " rows)");
	}

	private Object[][] convertTo2DArray(Object table)
			throws DoodleRenderException {
		if (table.getClass().isArray())
			return castTo2DimArr(table);
		if (table instanceof Collection)
			return collectionToArray((Collection<?>) table);
		throw new DoodleRenderException("TablePlugin cannot render this type.");
	}

	private Object[][] collectionToArray(Collection<?> collection) {
		Object[][] arrays = new Object[collection.size()][];
		Iterator<?> iterator = collection.iterator();
		for (int i = 0; i < arrays.length; i++) {
			arrays[i] = ((Collection<?>) iterator.next()).toArray();
		}
		return arrays;
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
	public String getCSS() {
		String elements = ".TablePlugin td, .CollectionPlugin td {float:none;} ";
		String nonOddRows = ".TablePlugin, .CollectionPlugin {background-color: white;}";
		String oddRows = ".TablePlugin .oddRow, .CollectionPlugin .oddRow {background-color:#eee}";
		String before = ".TablePlugin .beforeDecimalPoint, .CollectionPlugin .beforeDecimalPoint {text-align:right; padding-left: 4px;} ";
		String point = ".TablePlugin .decimalPoint, .CollectionPlugin .decimalPoint {text-align:center;}";
		String after = ".TablePlugin .afterDecimalPoint, .CollectionPlugin .afterDecimalPoint {text-align:left; padding-right: 4px; border-right: 1px solid #eee;} ";
		String oddAfter = ".TablePlugin .oddRow .afterDecimalPoint, .CollectionPlugin .oddRow .afterDecimalPoint {border-right: 1px solid #fff;}";
		return elements + nonOddRows + oddRows + before + point + after
				+ oddAfter;
	}

}
