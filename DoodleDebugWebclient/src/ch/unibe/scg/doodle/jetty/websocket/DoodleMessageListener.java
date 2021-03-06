package ch.unibe.scg.doodle.jetty.websocket;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import ch.unibe.scg.doodle.OutputManager;
import ch.unibe.scg.doodle.properties.DoodleDebugProperties;

/**
 * Listener for browser which DoodleDebug renders into. Listens to location
 * changes (link calls for instance) and checks if it contains a message for the
 * DoodleDebug server instance.
 * 
 * @author Cedric Reichenbach
 * 
 */
public class DoodleMessageListener {

	private static final String DOODLE_DEBUG_TYPE = "doodledebug";
	private static final String JAVA_FILE_LINK_TYPE = "javafile";
	private static final String EXTERNAL_LINK_TYPE = "extlink";
	private static final String LOAD_IMAGE_TYPE = "loadimage";
	private static final String APP_LOG_TYPE = "applog";

	private static int LIGHTBOX_CLOSE = -1;
	private static final int LIGHTBOX_STACK_OFFSET = -10;

	private static final String HISTORY_REQUEST = "dd:gethistory";
	private static final String PING = "ping";
	private static final String PONG = "pong";

	private DoodleSocket socket;
	private ClientCustodian clientCustodian;

	public DoodleMessageListener(DoodleSocket socket,
			ClientCustodian clientCustodian) {
		this.socket = socket;
		this.clientCustodian = clientCustodian;
	}

	public void handle(String message) {
		if (PING.equals(message))
			socket.sendMessage(PONG);
		else if (HISTORY_REQUEST.equals(message))
			clientCustodian.listFullHistory();
		else
			handlePrefixed(message.split(":", 2)[0], message.split(":", 2)[1]);
	}

	public void handlePrefixed(String type, String message) {
		if (type.equals(EXTERNAL_LINK_TYPE))
			handleExternalLink(message);
		else if (type.equals(DOODLE_DEBUG_TYPE))
			handleDoodledebugLocationEvent(message);
		else if (type.equals(JAVA_FILE_LINK_TYPE))
			handleJavaFileLocationEvent(message);
		else if (type.equals(LOAD_IMAGE_TYPE))
			handleLoadImageEvent(message);
		else if (type.equals(APP_LOG_TYPE))
			handleAppLogChosenEvent(message);
	}

	void handleExternalLink(String message) {
		// external links should be handled in front-end directly now
		throw new NotImplementedException();
	}

	private void handleDoodledebugLocationEvent(String message) {
		try {
			int id = Integer.parseInt(message);
			if (DoodleDebugProperties.developMode())
				System.out.println("SERVER: Received message from javascript: "
						+ id);

			if (id == LIGHTBOX_CLOSE) {
				clientCustodian.lightboxClosed();
				return;
			} else if (id < 0) {
				int fromZero = (-id + LIGHTBOX_STACK_OFFSET);
				int toCutOff = fromZero * -(fromZero % 2 * 2 - 1) / 2;
				clientCustodian.cutFromStack(toCutOff);
				return;
			}

			clientCustodian.drawInLightbox(id);
		} catch (NumberFormatException e) {
			// nothing to do here (must have been a real link)
		}
	}

	private void handleJavaFileLocationEvent(String message) {
		// not possible anymore
		throw new NotImplementedException();
	}

	private void handleLoadImageEvent(String message) {
		int id = Integer.parseInt(message);
		socket.executeJSOnClient(OutputManager.instance().loadImage(id));
	}

	@Deprecated
	private void handleAppLogChosenEvent(String message) {
		throw new NotImplementedException();
		// ApplicationUtil.setApplicationName(message);
		//
		// OutputManager.instance();
		// new BusyReader(new DoodleDatabase(), 1000);
	}

}
