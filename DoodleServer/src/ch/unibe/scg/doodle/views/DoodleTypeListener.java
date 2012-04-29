package ch.unibe.scg.doodle.views;

import org.eclipse.swt.browser.OpenWindowListener;
import org.eclipse.swt.browser.WindowEvent;

public class DoodleTypeListener implements OpenWindowListener {

	@Override
	public void open(WindowEvent event) {
		System.out.println("I should now render an object, but I don't know it.");
		// TODO: implement
	}

}
