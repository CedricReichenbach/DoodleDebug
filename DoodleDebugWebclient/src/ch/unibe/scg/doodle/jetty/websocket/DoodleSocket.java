package ch.unibe.scg.doodle.jetty.websocket;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketError;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;

@WebSocket
public class DoodleSocket {

	public static final String EXECUTE_JS_PREFIX = "executejs:";

	private Queue<String> messageQueue;
	private Session session;

	public DoodleSocket() {
		messageQueue = new LinkedList<>();
		WebSocketSupervisor.register(this);
	}

	@OnWebSocketClose
	public void onClose(int statusCode, String reason) {
		System.out.println("Websocket closed: statusCode=" + statusCode
				+ ", reason=" + reason);
	}

	@OnWebSocketError
	public void onError(Throwable t) {
		System.out.println("Websocket error: " + t.getMessage());
	}

	@OnWebSocketConnect
	public void onConnect(Session session) {
		this.session = session;
		while (messageQueue.size() > 0)
			sendMessage(messageQueue.poll());
		System.out.println("Websocket connected: "
				+ session.getRemoteAddress().getAddress());
	}

	@OnWebSocketMessage
	public void onMessage(String message) {
		// TODO
	}

	public void sendMessage(String message) {
		// FIXME: Find better solution
		if (session == null) {
			messageQueue.add(message);
			return;
		}

		try {
			// FIXME: Something's strange here
			session.getRemote().sendString(message);
		} catch (IOException e) {
			System.err
					.println("Failed to send message to client via WebSocket");
			e.printStackTrace();
		}
	}
}
