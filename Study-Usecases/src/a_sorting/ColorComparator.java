package a_sorting;

import java.awt.Color;
import java.util.Comparator;

public class ColorComparator implements Comparator<Color> {

	@Override
	public int compare(Color c1, Color c2) {
		if (brightness(c1) > brightness(c2))
			return 1;
		else if (brightness(c1) == brightness(c2))
			return 0;
		else
			return -1;
	}

	public int brightness(Color c) {
		return c.getRGB() == -16777216 ? 255 : (c.getBlue() + c.getGreen() + c
				.getRed()) / 3;
	}

}
