package ch.unibe.scg.doodle.server.views;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import ch.unibe.scg.doodle.OutputManager;
import ch.unibe.scg.doodle.database.BusyReader;
import ch.unibe.scg.doodle.database.DoodleDatabase;
import ch.unibe.scg.doodle.properties.DoodleDebugProperties;
import ch.unibe.scg.doodle.server.DoodleServer;
import ch.unibe.scg.doodle.util.ApplicationUtil;

/**
 * Listener for browser which DoodleDebug renders into. Listens to location
 * changes (link calls for instance) and checks if it contains a message for the
 * DoodleDebug server instance.
 * 
 * @author Cedric Reichenbach
 * 
 */
public class DoodleMessageListener {

	public void handle(String type, String message) {
		if (type.equals(DoodleLocationCodes.EXTERNAL_LINK_PREFIX))
			handleExternalLink(message);
		else if (type.equals(DoodleLocationCodes.DOODLE_DEBUG_PREFIX))
			handleDoodledebugLocationEvent(message);
		else if (type.equals(DoodleLocationCodes.JAVA_FILE_LINK_PREFIX))
			handleJavaFileLocationEvent(message);
		else if (type.equals(DoodleLocationCodes.LOAD_IMAGE_PREFIX))
			handleLoadImageEvent(message);
		else if (type.equals(DoodleLocationCodes.APP_LOG_PREFIX))
			handleAppLogChosenEvent(message);
	}

	void handleExternalLink(String message) {
		// external links should be handled in front-end directly now
		throw new NotImplementedException();
	}

	private void handleDoodledebugLocationEvent(String message) {
		try {
			DoodleServer doodleServer = DoodleServer.instance();

			int id = Integer.parseInt(message);
			if (DoodleDebugProperties.developMode())
				System.out.println("SERVER: Received message from javascript: "
						+ id);

			if (id == DoodleLocationCodes.LIGHTBOX_CLOSE) {
				doodleServer.lightboxClosed();
				return;
			} else if (id < 0) {
				int fromZero = (-id + DoodleLocationCodes.LIGHTBOX_STACK_OFFSET);
				int toCutOff = fromZero * -(fromZero % 2 * 2 - 1) / 2;
				doodleServer.cutoffFromStack(toCutOff);
				return;
			}

			doodleServer.drawObjectWithID(id);
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
		OutputManager.instance().loadImage(id);
	}

	private void handleAppLogChosenEvent(String message) {
		ApplicationUtil.setApplicationName(message);

		OutputManager.instance();
		new BusyReader(new DoodleDatabase(), 1000);
	}

}
