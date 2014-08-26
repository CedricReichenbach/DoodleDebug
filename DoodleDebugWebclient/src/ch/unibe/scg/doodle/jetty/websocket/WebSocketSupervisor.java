package ch.unibe.scg.doodle.jetty.websocket;

import java.util.ArrayList;
import java.util.List;

/**
 * Knows all active <code>DoodleSocket</code>s. Dynamic broadcasts to clients
 * (e.g. new doodles) should go through this class.
 * 
 * @author Cedric Reichenbach
 *
 */
public class WebSocketSupervisor {

	private static List<DoodleSocket> sockets = new ArrayList<>();

	static void register(DoodleSocket socket) {
		sockets.add(socket);
	}

	/**
	 * @deprecated Should not be generic, but client-specific.
	 * @param javascript
	 */
	@Deprecated
	public static void executeJavascript(String javascript) {
		executeJavascriptForAll(javascript);
	}

	public static void executeJavascriptForAll(String javascript) {
		for (DoodleSocket socket : sockets)
			socket.sendMessage(DoodleSocket.EXECUTE_JS_PREFIX + javascript);
	}

}
