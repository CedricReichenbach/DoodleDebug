package ch.unibe.scg.doodle.database;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Collection;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import ch.unibe.scg.doodle.database.hbase.HBaseIntMap;
import ch.unibe.scg.doodle.util.ApplicationUtil;

public class HBaseMapTest {

	private HBaseIntMap<String> hbaseMap;

	@Before
	public void init() {
		hbaseMap = new HBaseIntMap<>(ApplicationUtil.getApplicationName(), "junit_test_table");
	}

	@Test
	public void testPutRemove() {
		final int key = 123;
		final String value = "test";

		hbaseMap.remove(key);
		assertFalse(hbaseMap.containsKey(key));

		hbaseMap.put(key, value);
		assertTrue(hbaseMap.containsKey(key));
		assertTrue(hbaseMap.containsValue(value));
		assertEquals(value, hbaseMap.get(key));

		hbaseMap.remove(key);
		assertFalse(hbaseMap.containsKey(key));
	}

	@Test
	public void testClearAndFill() {
		hbaseMap.clear();
		assertTrue(hbaseMap.isEmpty());

		hbaseMap.put(321, "test");
		hbaseMap.put(456, "another");
		assertEquals(2, hbaseMap.size());

		hbaseMap.clear();
		assertTrue(hbaseMap.isEmpty());
	}

	@Test
	public void testKeysValues() {
		int key1 = -1, key2 = 256;
		String val1 = "Hell, no!", val2 = "Uhm, yes...";

		hbaseMap.clear();
		hbaseMap.put(key1, val1);
		hbaseMap.put(key2, val2);

		Set<Integer> keys = hbaseMap.keySet();
		assertEquals(2, keys.size());
		assertTrue(keys.contains(key1));
		assertTrue(keys.contains(key2));

		Collection<String> values = hbaseMap.values();
		assertEquals(2, values.size());
		assertTrue(values.contains(val1));
		assertTrue(values.contains(val2));
	}

}
