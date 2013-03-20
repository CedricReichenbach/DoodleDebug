package demo_simple;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

public class DemoSimpleMain {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Collection<Color> colors = new ArrayList<>();
		colors.add(Color.RED);
		colors.add(Color.YELLOW);
		colors.add(Color.BLACK);
		colors.add(randomColor());
		colors.add(randomColor());
		colors.add(randomColor());

		System.out.println(colors);
	}

	private static Color randomColor() {
		return new Color(random(255), random(255), random(255), random(255));
	}

	private static int random(int max) {
		return new Random().nextInt(max);
	}

}
