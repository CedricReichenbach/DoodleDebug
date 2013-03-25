package collection;

import java.util.HashMap;
import java.util.Map;

import ch.unibe.scg.doodle.Doo;

public class CollectonMain {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String[] array = { "uno", "due", "tre", "quattro", "cinque", "sei",
				"sette", "otto", "nove", "dieci" };
		String[] array2 = { "uno", "due", "tre", "quattro", "cinque", "sei" };
		String[] array3 = { "uno", "due", "tre", "quattro", "cinque", "sei",
				"sette" };
		String[] array4 = { "uno", "due" };
		String[] array5 = { "uno", "due", "tre" };

		Map<String, String[]> map1 = new HashMap<String, String[]>();
		map1.put("Mammals", array);
		map1.put("Reptiles", array2);
		map1.put("Amphibians", array5);

		Map<String, String[]> map2 = new HashMap<String, String[]>();
		map2.put("Books", array3);
		map2.put("Movies", array4);

		Map<?, ?>[] maps = { map1, map2 };
		Doo.dle(maps);
	}

}
