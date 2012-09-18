package a_sorting;

import static org.junit.Assert.assertEquals;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class SortingTest {

	@Test
	public void testSorting() {
		List<Color> colors = new ArrayList<Color>();
		colors.add(greyscale(10));
		colors.add(greyscale(0));
		colors.add(greyscale(200));
		colors.add(greyscale(50));

		List<Color> expectation = new ArrayList<Color>();
		expectation.add(greyscale(10));
		expectation.add(greyscale(0));
		expectation.add(greyscale(200));
		expectation.add(greyscale(50));

		assertEquals(colors, expectation);
	}

	public Color greyscale(int gamma) {
		return new Color(gamma, gamma, gamma);
	}
}
