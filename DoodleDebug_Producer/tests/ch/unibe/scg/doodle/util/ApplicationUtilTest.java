package ch.unibe.scg.doodle.util;

import org.junit.Test;

import static org.junit.Assert.*;

public class ApplicationUtilTest {
	@Test
	public void testGetApplicationName() {
		// XXX: Maybe not so smart
		assertEquals("org.eclipse.jdt.internal.junit.runner.RemoteTestRunner",
				ApplicationUtil.getApplicationName());
	}
}
