package ch.unibe.scg.doodle;

import javax.inject.Inject;

import ch.unibe.scg.doodle.api.Doodleable;
import ch.unibe.scg.doodle.helperClasses.ErrorDrawer;

import com.google.inject.assistedinject.Assisted;

public class SmallScratch extends RealScratch {

	/**
	 * Creates a new SmallScratch for visualizing objects
	 */
	@Inject
	SmallScratch(@Assisted Object o) {
		super(o);
	}

	protected void drawDoodleable() {
		try {
			((Doodleable) object).drawSmallOn(this.canvas);
		} catch (Exception e) {
			ErrorDrawer errorDrawer = new ErrorDrawer(e);
			try {
				errorDrawer.drawSmallOn(this.canvas);
			} catch (Exception e1) {
				throw new RuntimeException(e1);
			}
		}
	}

}
