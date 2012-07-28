package ch.unibe.scg.doodle.rendering;

import javax.inject.Inject;

import ch.unibe.scg.doodle.Doodler;
import ch.unibe.scg.doodle.htmlgen.Tag;

public class TwoDimArrayRendering implements Rendering<Object[][]> {

	@Inject
	Doodler doodler;
	private boolean onlyNumbers = false;

	@Override
	public void render(Object[][] arrays, Tag tag) {
		if (onlyNumbers(arrays)) {
			onlyNumbers = true;
		}

		Tag table = new Tag("table", "cellpadding=0");
		table.addAttribute("cellspacing", "0");
		Tag tbody = new Tag("tbody");
		int rowNumber = 0;
		for (Object[] array : arrays) {
			Tag row = new Tag("tr");
			if (rowNumber % 2 == 1) // odd
				row.addCSSClass("oddRow");
			renderRow(array, row);
			tbody.add(row);
			rowNumber++;
		}
		table.add(tbody);
		tag.add(table);
	}

	@Override
	public void renderSmall(Object[][] arrays, Tag tag) {
		int rows = arrays.length;
		tag.add("table (" + rows + " rows)");
	}

	private boolean onlyNumbers(Object[][] arrays) {
		for (Object[] array : arrays) {
			for (Object element : array) {
				if (!isNumber(element)) {
					return false;
				}
			}
		}
		return true;
	}

	private boolean isNumber(Object o) {
		return (o instanceof Float || o instanceof Double
				|| o instanceof Integer || o instanceof Short || o instanceof Byte);
	}

	private void renderRow(Object[] array, Tag row) {
		for (Object element : array) {
			if (onlyNumbers) {
				renderNumber(element, row);
			} else {
				Tag data = new Tag("td");

				doodler.renderInlineIntoWithoutClassName(element, data);
				row.add(data);
			}
		}
	}

	private void renderNumber(Object number, Tag row) {
		Tag before = new Tag("td", "class=beforeDecimalPoint");
		before.add(beforeDecPoint(number));
		row.add(before);

		Tag point = new Tag("td", "class=decimalPoint");
		Tag after = new Tag("td", "class=afterDecimalPoint");
		if (!afterDecPoint(number).equals("")
				&& !afterDecPoint(number).equals("0")) {
			point.add(".");
			after.add(afterDecPoint(number));
		}
		row.add(point);
		row.add(after);
	}

	private int beforeDecPoint(Object number) {
		return (int) (double) Double.valueOf(number.toString());
	}

	private String afterDecPoint(Object number) {
		String numString = number.toString();
		int index = numString.indexOf('.');
		if (index < 0)
			return "";
		else
			return numString.substring(index + 1);
	}

}
