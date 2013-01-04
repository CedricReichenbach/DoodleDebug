package ch.unibe.scg.doodle.rendering;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Iterator;

import javax.inject.Inject;
import javax.swing.text.NumberFormatter;

import ch.unibe.scg.doodle.Doodler;
import ch.unibe.scg.doodle.htmlgen.Tag;

public class TableRendering implements Rendering<Collection<Collection<?>>> {

	@Inject
	Doodler doodler;
	private boolean numberTable = false;

	@Override
	public void render(Collection<Collection<?>> collections, Tag tag) {
		if (numberTable(collections)) {
			numberTable = true;
			tag.addCSSClass("numberTable");
		}

		Tag table = new Tag("table", "cellpadding=0");
		table.addAttribute("cellspacing", "0");
		Tag tbody = new Tag("tbody");
		int rowNumber = 0;
		for (Collection<?> collection : collections) {
			Tag row = new Tag("tr");
			if (rowNumber % 2 == 1) // odd
				row.addCSSClass("oddRow");
			renderRow(collection, row);
			tbody.add(row);
			rowNumber++;
		}
		table.add(tbody);
		tag.add(table);
	}

	@Override
	public void renderSimplified(Collection<Collection<?>> collections, Tag tag) {
		int rows = collections.size();
		tag.add("table (" + rows + " rows)");
	}

	private boolean numberTable(Collection<Collection<?>> collections) {
		if (!rectangular(collections))
			return false;
		for (Collection<?> collection : collections) {
			for (Object element : collection) {
				if (!isNumber(element)) {
					return false;
				}
			}
		}
		return true;
	}

	private boolean rectangular(Collection<Collection<?>> collections) {
		if (collections.isEmpty())
			return true;
		Iterator<Collection<?>> iterator = collections.iterator();
		final int width = iterator.next().size();
		while (iterator.hasNext()) {
			if (iterator.next().size() != width) {
				return false;
			}
		}
		return true;
	}

	private boolean isNumber(Object o) {
		return o instanceof Number;
	}

	// private void renderRow(List<?> l, Tag row) {
	// if(numberTable) {
	// List<Number> numbers = (List<Number>) l;
	//
	// }
	// }

	private void renderRow(Collection<?> collection, Tag row) {
		for (Object element : collection) {
			if (numberTable) {
				renderNumber((Number) element, row);
			} else {
				Tag data = new Tag("td");

				doodler.renderInlineIntoWithoutClassName(element, data);
				row.add(data);
			}
		}
	}

	private void renderNumber(Number number, Tag row) {
		Tag before = new Tag("td", "class=beforeDecimalPoint");
		if (number.doubleValue() < 0) {
			before.add("-");
		}
		before.add(beforeDecPoint(number));
		row.add(before);

		Tag point = new Tag("td", "class=decimalPoint");
		Tag after = new Tag("td", "class=afterDecimalPoint");
		if (isFloatingPointNumber(number)) {
			point.add(".");
			if (somethingAfterComma(number))
				after.add(afterDecPoint(number));
		}
		row.add(point);
		row.add(after);
	}

	private boolean isFloatingPointNumber(Number number) {
		return number instanceof Double || number instanceof Float
				|| number instanceof BigDecimal || somethingAfterComma(number);
	}

	private boolean somethingAfterComma(Number number) {
		return !afterDecPoint(number).equals("")
				&& !afterDecPoint(number).equals("0");
	}

	private int beforeDecPoint(Number number) {
		return number.intValue();
	}

	private String afterDecPoint(Number number) {
		String numString = number.toString();
		int index = numString.indexOf('.');
		if (index < 0)
			return "";
		else
			return numString.substring(index + 1);
	}

}
