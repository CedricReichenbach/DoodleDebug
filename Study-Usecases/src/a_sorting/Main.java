package a_sorting;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		List<Color> colors = new ArrayList<Color>();
		colors.add(greyscale(100));
		colors.add(greyscale(0));
		colors.add(greyscale(200));
		colors.add(greyscale(250));
		colors.add(greyscale(50));
		SortingUtil.sort(colors);

		List<Color> expectation = new ArrayList<Color>();
		expectation.add(greyscale(0));
		expectation.add(greyscale(50));
		expectation.add(greyscale(100));
		expectation.add(greyscale(200));
		expectation.add(greyscale(250));

		check(colors.equals(expectation));
	}

	private static void check(boolean valid) {
		if (!valid)
			throw new AssertionError();
	}

	public static Color greyscale(int gamma) {
		return new Color(gamma, gamma, gamma);
	}

}
