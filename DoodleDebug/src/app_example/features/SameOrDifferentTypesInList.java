package app_example.features;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ch.unibe.scg.doodle.Doo;


public class SameOrDifferentTypesInList {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//XXX is there a unit test in here?
		List<Object> different = new ArrayList<Object>();
		
		different.add("hello");
		different.add(100);
		
		List<String> same = Arrays.asList("Asdf", "jklö");
		
		Doo.dle(different);
		Doo.dle(same);
	}

}
