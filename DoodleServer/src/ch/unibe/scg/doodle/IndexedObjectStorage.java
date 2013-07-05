package ch.unibe.scg.doodle;

import ch.unibe.scg.doodle.helperClasses.Nullable;

/**
 * Storage for objects to be possibly rendered later (clickables). Every stored
 * object maps exactly to one ID.
 * 
 * @author Cedric Reichenbach
 * 
 */
public final class IndexedObjectStorage {
	static final int CAPACITY = 10000;
	private final Object[] ringBuffer;
	private int nextID;

	public IndexedObjectStorage() {
		this.nextID = 0;
		this.ringBuffer = new Object[CAPACITY];
	}

	/** @return Id of stored object. */
	public int store(Object o) {
		ringBuffer[nextID % CAPACITY] = o;
		return nextID++;
	}

	public @Nullable Object get(int id) {
		if (id < nextID - CAPACITY || id >= nextID)
			return null;
		
		return ringBuffer[id % CAPACITY];
	}
}
