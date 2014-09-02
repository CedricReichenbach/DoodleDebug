package ch.unibe.scg.doodle.plugins;

import static ch.unibe.scg.doodle.util.ArrayUtil.asList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Provider;

import ch.unibe.scg.doodle.htmlgen.Tag;
import ch.unibe.scg.doodle.rendering.DoodleRenderException;
import ch.unibe.scg.doodle.rendering.TableRendering;

/**
 * Plugin responsible for rendering of two-dimensional data structures like
 * tables, matrices and 2-dimensional arrays.
 * 
 * @author Cedric Reichenbach
 * 
 */
public class TablePlugin extends ArrayPlugin {

	@Inject
	Provider<TableRendering> tableRenderingProvider;

	@Override
	public Set<Class<?>> getDrawableClasses() {
		// detected by renderingRegistry directly
		HashSet<Class<?>> hs = new HashSet<Class<?>>();
		return hs;
	}

	@Override
	public void render(Object table, Tag tag) throws DoodleRenderException {
		Collection<Collection<?>> arrays = convertTo2DCollection(table);
		tableRenderingProvider.get().render(arrays, tag);
	}

	@Override
	public void renderSimplified(Object table, Tag tag)
			throws DoodleRenderException {
		Collection<Collection<?>> arrays = convertTo2DCollection(table);
		tableRenderingProvider.get().render(arrays, tag);
	}

	@SuppressWarnings("unchecked")
	private Collection<Collection<?>> convertTo2DCollection(Object table)
			throws DoodleRenderException {
		try {
			if (table.getClass().isArray())
				return twoDimArrayToCollection(table);
			if (table instanceof Collection) {
				return (Collection<Collection<?>>) table;
			}
		} catch (ClassCastException e) {
			throw new DoodleRenderException(e);
		}
		throw new DoodleRenderException("TablePlugin cannot render this type.");
	}

	private Collection<Collection<?>> twoDimArrayToCollection(Object table) {
		Collection<Collection<?>> result = new ArrayList<Collection<?>>();
		Collection<Object> arrays = asList(table);
		for (Object array : arrays) {
			result.add(asList(array));
		}
		return result;
	}

	@Override
	public String getCSS() {
		String table = ".TablePlugin table, .CollectionPlugin table {empty-cells:show;}";
		String elements = ".TablePlugin td, .CollectionPlugin td {float:none; padding: 2px;} ";
		String numberElements = ".TablePlugin.numberTable td, .CollectionPlugin.numberTable td {float:none; padding: 1px 0;} ";
		String oddRows = ".TablePlugin .oddRow, .CollectionPlugin .oddRow {background-color:rgba(153,153,153,0.17)}";
		String before = ".TablePlugin.numberTable .beforeDecimalPoint, .CollectionPlugin.numberTable .beforeDecimalPoint {text-align:right; padding-left: 8px;} ";
		String point = ".TablePlugin.numberTable .decimalPoint, .CollectionPlugin.numberTable .decimalPoint {text-align:center;}";
		String after = ".TablePlugin.numberTable .afterDecimalPoint, .CollectionPlugin.numberTable .afterDecimalPoint "
				+ "{text-align:left; padding-right: 8px; border-right: 1px solid rgba(153,153,153,0.17);} ";
		String oddAfter = ".TablePlugin.numberTable .oddRow .afterDecimalPoint, .CollectionPlugin.numberTable .oddRow .afterDecimalPoint "
				+ "{border-right: 1px solid white;}"
				+ ".dark .TablePlugin.numberTable .oddRow .afterDecimalPoint, .dark .CollectionPlugin.numberTable .oddRow .afterDecimalPoint "
				+ "{border-right: 1px solid #333;}";
		return table + elements + numberElements + oddRows
				+ before + point + after + oddAfter;
	}
}
