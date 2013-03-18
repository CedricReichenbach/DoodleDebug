package demo.util;

public class IdFactory {
	private static int currentId = 0;

	public static int nextId() {
		return currentId++;
	}
}
