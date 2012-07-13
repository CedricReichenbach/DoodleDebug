package ch.unibe.scg.doodle.server.views;

import org.eclipse.swt.browser.LocationEvent;
import org.eclipse.swt.browser.LocationListener;

import ch.unibe.scg.doodle.server.DoodleServer;
import ch.unibe.scg.doodle.simon.SimonServer;

public class DoodleLocationListener implements LocationListener {

	@Override
	public void changing(LocationEvent event) {
		try {
			DoodleServer doodleServer = DoodleServer.instance();

			int id = Integer
					.parseInt(event.location.replaceFirst("about:", ""));
			System.out
					.println("SERVER: Now attempting to draw object with ID: "
							+ id);
			event.doit = false; // prevent any actual changing of location

			if (id == DoodleLocationCodes.LIGHTBOX_CLOSE) {
				doodleServer.lightboxClosed();
				return;
			}
			if (id < 0) {
				doodleServer.cutoffFromStack(-id
						+ DoodleLocationCodes.LIGHTBOX_STACK_OFFSET);
				return;
			}

			doodleServer.drawObjectWithID(id);
		} catch (NumberFormatException e) {
			// nothing to do here (must have been a real link)
		}
	}

	@Override
	public void changed(LocationEvent event) {
		// TODO Auto-generated method stub

	}

}
