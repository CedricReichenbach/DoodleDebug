package ch.unibe.scg.doodle.startup;

import ch.unibe.scg.doodle.simon.SimonServer;

public class Main {

	private static SimonServer simonServer;

	public static void main(String[] args) {
		if (ArgParser.isStartCommand(args))
			startSimonServer();
		else if (ArgParser.isStopCommand(args))
			stopSimonServer();
		else
			ArgParser.printInstructions();
	}

	private static void startSimonServer() {
		try {
			System.out.println("Starting SIMON server...");
			simonServer = new SimonServer();
			System.out.println("Server started successfully.");
		} catch (Exception e) {
			System.out.println("Server could not be started.");
			e.printStackTrace();
		}
	}

	private static void stopSimonServer() {
		// FIXME: I don't think this will work... :D
		simonServer.stop();
	}

}
