package app_example.huge_strings;

import java.util.ArrayList;

import doodle.D;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ArrayList<String> list = new ArrayList<String>();
		
		list.add("This is a veeery looooooooooooooooong looong looong sentence..........");
		
		list.add("short one");
		
		D.raw(list);
	}

}
