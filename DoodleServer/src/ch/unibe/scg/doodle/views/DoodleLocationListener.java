package ch.unibe.scg.doodle.views;

import org.eclipse.swt.browser.LocationEvent;
import org.eclipse.swt.browser.LocationListener;

import ch.unibe.scg.doodle.server.DoodleServer;
import ch.unibe.scg.doodle.simon.SimonServer;

public class DoodleLocationListener implements LocationListener {

	@Override
	public void changing(LocationEvent event) {
		// TODO
		try {
			int id = Integer
					.parseInt(event.location.replaceFirst("about:", ""));
			System.out.println("Now attempting to draw object with ID: " + id);
			event.doit = false; // prevent any actual changing of location
			
			SimonServer.instance.subRenderObject(id);
			
//			DoodleServer.instance().drawObjectWithID(id);
		} catch (NumberFormatException e) {
			// nothing to do here (must have been a real link)
		}
	}

	@Override
	public void changed(LocationEvent event) {
		// TODO Auto-generated method stub
		
	}

}
