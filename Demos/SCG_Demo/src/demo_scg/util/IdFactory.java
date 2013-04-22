package demo_scg.util;

public class IdFactory {
	private static int currentId = 0;

	public static int nextId() {
		return currentId++;
	}
}
