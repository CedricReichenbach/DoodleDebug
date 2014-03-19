package ch.unibe.scg.doodle;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Assert;
import org.junit.Test;

public class IndexedObjectStorageTest {
	@Test
	public void testStore() {
		IndexedObjectStorage storage = new IndexedObjectStorage();
		for (int i = 0; i <= 2 * IndexedObjectStorage.CAPACITY; i++) {
			storage.store(i);
		}
		for (int i = 0; i <= IndexedObjectStorage.CAPACITY; i++) {
			Assert.assertNull(storage.get(i));
		}
		for (int i = IndexedObjectStorage.CAPACITY + 1; i <= 2 * IndexedObjectStorage.CAPACITY; i++) {
			assertThat(storage.get(i), is((Object) i));
		}
		for (int i = 2 * IndexedObjectStorage.CAPACITY + 1; i <= 3 * IndexedObjectStorage.CAPACITY; i++) {
			Assert.assertNull(storage.get(i));
		}
	}
}
