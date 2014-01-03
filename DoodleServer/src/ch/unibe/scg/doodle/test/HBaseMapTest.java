package ch.unibe.scg.doodle.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import ch.unibe.scg.doodle.hbase.HBaseMap;

public class HBaseMapTest {

	private HBaseMap hbaseMap;

	@Before
	public void init() {
		hbaseMap = new HBaseMap("junit_test_table");
	}

	@Test
	public void testPutRemove() {
		final int key = 123;
		final String value = "test";

		hbaseMap.remove(key);
		assertFalse(hbaseMap.containsKey(key));

		hbaseMap.put(key, value);
		assertTrue(hbaseMap.containsKey(key));
		assertEquals(value, hbaseMap.get(key));

		hbaseMap.remove(key);
		assertFalse(hbaseMap.containsKey(key));
	}

}
