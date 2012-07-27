package ch.unibe.scg.doodle.rendering;

import javax.inject.Inject;

import ch.unibe.scg.doodle.Doodler;
import ch.unibe.scg.doodle.htmlgen.Tag;

public class TwoDimArrayRendering implements Rendering<Object[][]> {

	@Inject
	Doodler doodler;

	@Override
	public void render(Object[][] arrays, Tag tag) {
		Tag table = new Tag("table");
		Tag tbody = new Tag("tbody");
		for (Object[] array : arrays) {
			Tag row = new Tag("tr");
			renderRow(array, row);
			tbody.add(row);
		}
		table.add(tbody);
		tag.add(table);
	}

	@Override
	public void renderSmall(Object[][] arrays, Tag tag) {
		// TODO Auto-generated method stub

	}

	private void renderRow(Object[] array, Tag row) {
		for (Object element : array) {
			Tag data = new Tag("td");
			doodler.renderInlineIntoWithoutClassName(element, data);
			row.add(data);
		}
	}

}
