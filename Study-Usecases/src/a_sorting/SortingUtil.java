package a_sorting;

import java.awt.Color;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SortingUtil {

	public static void sort(List<Color> colors) {
		Comparator<Color> comp = new ColorComparator();
		Collections.sort(colors, comp);
	}
}
