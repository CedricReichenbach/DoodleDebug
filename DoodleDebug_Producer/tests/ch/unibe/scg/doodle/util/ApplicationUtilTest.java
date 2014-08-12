package ch.unibe.scg.doodle.util;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ApplicationUtilTest {
	@Test
	public void testGetApplicationName() {
		// XXX: Maybe not so smart
		assertEquals("org.eclipse.jdt.internal.junit.runner.RemoteTestRunner",
				ApplicationUtil.getApplicationName());
	}
}
