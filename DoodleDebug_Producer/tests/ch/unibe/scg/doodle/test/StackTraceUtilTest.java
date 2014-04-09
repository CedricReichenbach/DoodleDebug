package ch.unibe.scg.doodle.test;

import static org.junit.Assert.*;

import org.junit.Test;

import ch.unibe.scg.doodle.util.StackTraceUtil;

public class StackTraceUtilTest {

	@Test
	public void testClassMatcher() {
		boolean isClass = StackTraceUtil.isClass("Asdf.java:123");
		assertTrue(isClass);
	}
}
