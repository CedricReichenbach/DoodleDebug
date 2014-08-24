package ch.unibe.scg.doodle;

import org.eclipse.jetty.server.Server;

import ch.unibe.scg.doodle.jetty.WebsiteHandler;

public class Main {

	public static void main(String[] args) throws Exception {
		Server server = new Server(8080);
		server.setHandler(new WebsiteHandler());
		server.start();
		server.join();
	}

}
