package ch.unibe.scg.doodle;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.websocket.server.WebSocketHandler;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;

import ch.unibe.scg.doodle.jetty.WebsiteHandler;
import ch.unibe.scg.doodle.jetty.websocket.DoodleSocket;

public class DoodleDebugWebapp {

	private static final int DEFAULT_PORT = 8080;
	public static WebSocketHandler webSocketHandler;

	public static void main(String[] args) throws Exception {
		if (args.length > 0) {
			if ("--help".equals(args[0]))
				printHelp();
			startServer(Integer.parseInt(args[0]));
		} else
			startServer();
	}

	private static void printHelp() {
		System.out.println("~~ DoodleDebug web application ~~");
		System.out.println();
		System.out.println("Without arguments: Start server on default port ("
				+ DEFAULT_PORT + ")");
		System.out.println("With one argument: Argument is server port");
		System.out.println();
		System.out
				.println("For further configuration, e.g. a custom database connection, "
						+ "you need embed this application into another and do any configuration "
						+ "before calling DoodleDebugWebapp.startServer([port]). More info: http://scg.unibe.ch/wiki/projects/DoodleDebug");
	}

	private static void startServer() throws Exception {
		startServer(DEFAULT_PORT);
	}

	private static void startServer(int port) throws Exception {
		Server server = new Server(port);

		webSocketHandler = new WebSocketHandler() {
			@Override
			public void configure(WebSocketServletFactory factory) {
				factory.getPolicy().setIdleTimeout(600000);
				factory.register(DoodleSocket.class);
			}
		};
		webSocketHandler.setHandler(new WebsiteHandler());

		server.setHandler(webSocketHandler);
		server.start();
		server.join();
	}

}
