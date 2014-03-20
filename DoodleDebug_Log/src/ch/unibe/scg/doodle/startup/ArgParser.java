package ch.unibe.scg.doodle.startup;

public class ArgParser {
	public static boolean isStartCommand(String[] args) {
		return isCommand("start", args);
	}
	
	public static boolean isStopCommand(String[] args) {
		return isCommand("stop", args);
	}

	private static boolean isCommand(String command, String[] args) {
		for (String arg : args)
			if (command.equals(arg))
				return true;
		return false;
	}

	public static void printInstructions() {
		System.out.println("Usage:");
		System.out.println(" start - Starts this DoodleDebug Log");
		System.out.println(" stop - Shuts this DoodleDebug Log down");
	}
}
