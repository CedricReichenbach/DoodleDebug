package ch.unibe.scg.doodle.jetty.websocket;

import java.io.IOException;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketError;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;

import ch.unibe.scg.doodle.properties.DoodleDebugProperties;

@WebSocket
public class DoodleSocket {

	private static final String EXECUTE_JS_PREFIX = "executejs:";

	private Session session;
	private DoodleMessageListener messageListener;
	private ClientCustodian clientCustodian;

	public DoodleSocket() {
		WebSocketSupervisor.register(this);
		this.clientCustodian = new ClientCustodian(this);
		this.messageListener = new DoodleMessageListener(this, clientCustodian);
	}

	@OnWebSocketClose
	public void onClose(int statusCode, String reason) {
		if (DoodleDebugProperties.developMode())
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
		if (DoodleDebugProperties.developMode())
			System.out.println("Websocket connected: "
					+ session.getRemoteAddress().getAddress());

		this.session = session;
	}

	@OnWebSocketMessage
	public void onMessage(String message) {
		if (DoodleDebugProperties.developMode())
			System.out.println("Websocket message received: " + message);

		messageListener.handle(message);
	}

	public void sendMessage(String message) {
		try {
			if (DoodleDebugProperties.developMode())
				System.out.println("Websocket sending: " + message);

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
