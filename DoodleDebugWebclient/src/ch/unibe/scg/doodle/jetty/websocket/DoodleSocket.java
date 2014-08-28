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

	private static final String EXECUTE_JS_PREFIX = "executejs:";

	private Queue<String> messageQueue;
	private Session session;
	private DoodleMessageListener messageListener;
	private ClientCustodian clientCustodian;

	public DoodleSocket() {
		messageQueue = new LinkedList<>();
		WebSocketSupervisor.register(this);
		this.clientCustodian = new ClientCustodian(this);
		this.messageListener = new DoodleMessageListener(this, clientCustodian);
	}

	@OnWebSocketClose
	public void onClose(int statusCode, String reason) {
		System.out.println("Websocket closed: statusCode=" + statusCode
				+ ", reason=" + reason);
		WebSocketSupervisor.remove(this);
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
		System.out.println("Websocket message received: " + message);
		messageListener.handle(message);
		// TODO
	}

	public void sendMessage(String message) {
		// FIXME: Find better solution
		if (session == null) {
			messageQueue.add(message);
			return;
		}

		try {
			System.out.println("Websocket sending: " + message);
			// FIXME: Something's strange here
			session.getRemote().sendString(message);
		} catch (IOException e) {
			System.err
					.println("Failed to send message to client via WebSocket");
			e.printStackTrace();
		}
	}

	public void executeJSOnClient(String javascript) {
		this.sendMessage(EXECUTE_JS_PREFIX + javascript);
	}
}
