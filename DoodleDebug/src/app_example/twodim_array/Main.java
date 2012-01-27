package app_example.twodim_array;

import java.awt.Color;

import doodle.D;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Color[][] colors = new Color[5][5];
		
		for (int i = 0; i < colors.length; i++) {
			colors[i] = randomColors();
		}

		System.out.println(colors);
		
		D.raw(colors);
	}

	private static Color[] randomColors() {
		Color[] arr = new Color[5];
		for (int i = 0; i < arr.length; i++) {
			arr[i] = randomColor();
		}
		return arr;
	}

	private static Color randomColor() {
		return new Color(randomInt(255), randomInt(255), randomInt(255));
	}

	private static int randomInt(int max) {
		return (int) (Math.random() * max);
	}
}
