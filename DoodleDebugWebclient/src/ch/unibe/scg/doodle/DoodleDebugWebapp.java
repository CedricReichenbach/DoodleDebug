package ch.unibe.scg.doodle;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.websocket.server.WebSocketHandler;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;

import ch.unibe.scg.doodle.jetty.WebsiteHandler;
import ch.unibe.scg.doodle.jetty.websocket.DoodleSocket;

public class DoodleDebugWebapp {

	private static final int DEFAULT_PORT = 8080;
	public static WebSocketHandler webSocketHandler;
	private static Server server;

	public static void main(String[] args) throws Exception {
		if (args.length > 0) {
			String firstArg = args[0];
			if ("--help".equals(firstArg)) {
				printHelp();
				return;
			} else if ("stop".equals(firstArg)) {
				stopServer();
				return;
			}
			startServer(Integer.parseInt(firstArg));
		} else
			startServer();
	}

	private static void printHelp() {
		System.out.println("~~ DoodleDebug web application ~~");
		System.out.println("With no arguments: Start server on default port ("
				+ DEFAULT_PORT + ")");
		System.out
				.println("With one argument: Argument is either \"stop\" or a number as server port");
		System.out
				.println("For further configuration, e.g. a custom database connection, "
						+ "you need to embed this application into another and do any configuration "
						+ "before calling DoodleDebugWebapp.startServer([port]). More info: http://scg.unibe.ch/wiki/projects/DoodleDebug");
	}

	public static void startServer() {
		startServer(DEFAULT_PORT);
	}

	public static void startServer(final int port) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				server = new Server(port);

				webSocketHandler = new WebSocketHandler() {
					@Override
					public void configure(WebSocketServletFactory factory) {
						factory.getPolicy().setIdleTimeout(600000);
						factory.register(DoodleSocket.class);
					}
				};
				webSocketHandler.setHandler(new WebsiteHandler());

				server.setHandler(webSocketHandler);
				try {
					server.start();
					System.out
							.println("DoodleDebug server running at http://localhost:"
									+ port);
					server.join();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public static void stopServer() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					server.stop();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}
