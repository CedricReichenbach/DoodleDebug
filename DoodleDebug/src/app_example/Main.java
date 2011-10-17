package app_example;

import java.awt.Color;
import java.util.ArrayList;

public class Main {

	/**
	 * Creates an ArrayList of colors and draws them with D.raw(...) of DoodleDebug
	 * @param args
	 */
	public static void main(String[] args) {
		ArrayList<Color> al = new ArrayList<Color>(5);
		fillRandomColors(al, 5);
		
		System.out.println(al);
		// TODO: D.raw it!
	}

	private static void fillRandomColors(ArrayList<Color> al, int n) {
		for (int i = 0; i < n; i++) {
			al.add(new Color(randomInt(255), randomInt(255), randomInt(255)));
		}
	}

	private static int randomInt(int max) {
		return (int)(Math.random()*max);
	}

}
