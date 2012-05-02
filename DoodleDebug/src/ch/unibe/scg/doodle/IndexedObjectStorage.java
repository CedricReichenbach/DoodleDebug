package ch.unibe.scg.doodle;

import java.util.HashMap;
import java.util.Map;

/**
 * Storage for objects to be possibly rendered later (clickables). Every stored
 * object maps exactly to one ID.
 * 
 * @author Cedric Reichenbach
 * 
 */
public class IndexedObjectStorage {
	private Map<Integer, Object> map;
	int nextID;

	public IndexedObjectStorage() {
		map = new HashMap<Integer, Object>();
		nextID = 0;
	}

	public int store(Object o) {
		map.put(nextID, o);
		return nextID++;
	}

	public Object get(int id) {
		return map.get(id);
	}
}
