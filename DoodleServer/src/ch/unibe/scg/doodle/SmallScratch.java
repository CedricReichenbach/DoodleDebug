package ch.unibe.scg.doodle;

import javax.inject.Inject;

import com.google.inject.assistedinject.Assisted;

public class SmallScratch extends RealScratch {

	/**
	 * Creates a new Scratch for visualizing objects
	 */
	@Inject
	SmallScratch(@Assisted Object o) {
		super(o);
	}

}
