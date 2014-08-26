package ch.unibe.scg.doodle;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.websocket.server.WebSocketHandler;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;

import ch.unibe.scg.doodle.jetty.WebsiteHandler;
import ch.unibe.scg.doodle.jetty.websocket.DoodleSocket;

public class Main {

	public static void main(String[] args) throws Exception {
		Server server = new Server(8080);

		WebSocketHandler webSocketHandler = new WebSocketHandler() {
			@Override
			public void configure(WebSocketServletFactory factory) {
				factory.register(DoodleSocket.class);
			}
		};
		webSocketHandler.setHandler(new WebsiteHandler());

		server.setHandler(webSocketHandler);
		server.start();
		server.join();
	}

}
