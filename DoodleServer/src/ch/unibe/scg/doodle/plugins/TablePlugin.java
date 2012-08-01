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
		HashSet<Class<?>> hs = new HashSet<Class<?>>();
		return hs;
	}

	@Override
	public void render(Object table, Tag tag) throws DoodleRenderException {
		Collection<Collection<?>> arrays = convertTo2DCollection(table);
		tableRenderingProvider.get().render(arrays, tag);
	}

	@Override
	public void renderSmall(Object table, Tag tag) throws DoodleRenderException {
		Collection<Collection<?>> arrays = convertTo2DCollection(table);
		tableRenderingProvider.get().render(arrays, tag);
	}

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
		String elements = ".TablePlugin td, .CollectionPlugin td {float:none;} ";
		String nonOddRows = ".TablePlugin, .CollectionPlugin {background-color: white;}";
		String oddRows = ".TablePlugin .oddRow, .CollectionPlugin .oddRow {background-color:#eee}";
		String before = ".TablePlugin .beforeDecimalPoint, .CollectionPlugin .beforeDecimalPoint {text-align:right; padding-left: 4px;} ";
		String point = ".TablePlugin .decimalPoint, .CollectionPlugin .decimalPoint {text-align:center;}";
		String after = ".TablePlugin .afterDecimalPoint, .CollectionPlugin .afterDecimalPoint {text-align:left; padding-right: 4px; border-right: 1px solid #eee;} ";
		String oddAfter = ".TablePlugin .oddRow .afterDecimalPoint, .CollectionPlugin .oddRow .afterDecimalPoint {border-right: 1px solid #fff;}";
		return table + elements + nonOddRows + oddRows + before + point + after
				+ oddAfter;
	}
}
