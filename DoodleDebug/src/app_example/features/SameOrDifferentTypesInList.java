package app_example.features;

import java.util.ArrayList;
import java.util.List;

import doodle.D;

public class SameOrDifferentTypesInList {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		List different = new ArrayList();
		
		different.add("hello");
		different.add(100);
		
		List same = new ArrayList<String>();
		
		same.add("Asdf");
		same.add("jklö");
		
		D.raw(different);
		D.raw(same);
	}

}
