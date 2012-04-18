package app_example.color_array;

import java.awt.Color;
import java.lang.reflect.Array;
import java.util.ArrayList;

import ch.unibe.scg.doodle.D;


public class Main {

	/**
	 * Creates an ArrayList of colors and draws them with D.raw(...) of
	 * DoodleDebug
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		Color[] ar = new Color[5];
		fillRandomColors(ar);
		
		D.raw(ar);

		ar[2] = new ExtendedColor(0);

		D.raw(ar);

		for (int i = 1; i <= 10; i++) {
			changeRandomColor(ar);
			D.raw(ar);
		}
	}

	private static void fillRandomColors(Color[] ar) {
		for (int i = 0; i < ar.length; i++) {
			ar[i] = randomColor();
		}
	}

	private static void changeRandomColor(Color[] ar) {
		ar[randomInt(ar.length)] = randomColor();
	}

	private static Color randomColor() {
		return new Color(randomInt(255), randomInt(255), randomInt(255));
	}

	private static int randomInt(int max) {
		return (int) (Math.random() * max);
	}

}
