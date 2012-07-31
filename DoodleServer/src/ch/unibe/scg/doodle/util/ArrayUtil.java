package ch.unibe.scg.doodle.util;

import java.lang.reflect.Array;

public class ArrayUtil {
	public static Object[] castToArray(Object array) {
		try {
			return (Object[]) array;
		} catch (ClassCastException e) { // was array of primitives
			return castPrimitivesArray(array);
		}
	}

	/**
	 * Prevent error for arrays of primitive data types. There seems to be no
	 * easier way.
	 * 
	 * @param array
	 * @return
	 */
	private static Object[] castPrimitivesArray(Object array) {
		int length = Array.getLength(array);
		Object[] casted = new Object[length];
		for (int i = 0; i < length; i++) {
			casted[i] = Array.get(array, i);
		}
		return casted;
	}
}
