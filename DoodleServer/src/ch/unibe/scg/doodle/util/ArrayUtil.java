package ch.unibe.scg.doodle.util;

import static ch.unibe.scg.doodle.util.ArrayUtil.castToArray;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collection;

public class ArrayUtil {

	/**
	 * Convert an array to a java.util.List. In contrast to Arrays.asList(),
	 * this one explicitly casts the given object to an array to prevent
	 * surprises.
	 * 
	 * @param array
	 * @return
	 */
	public static Collection<Object> asList(Object array) {
		return Arrays.asList(castToArray(array));
	}

	/**
	 * Cast an object to an array. This method includes correct casting of
	 * primitives-arrays.
	 * 
	 * @param array
	 * @return
	 */
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
