package ch.unibe.scg.doodle.util;

import java.io.InputStream;
import java.util.Scanner;

public class ClassUtil {

	public static boolean isThirdParty(Class<?> clazz) {
		// FIXME: Find a smarter way
		return !clazz.isArray() & !clazz.isPrimitive()
				& !clazz.getName().startsWith("java.");
	}

	public static String getBinary(Class<? extends Object> clazz) {
		InputStream stream = clazz.getResourceAsStream(clazz.getSimpleName()
				+ ".class");
		return convertStreamToString(stream);
	}

	private static String convertStreamToString(InputStream stream) {
		try (Scanner s = new Scanner(stream)) {
			String result = s.useDelimiter("\\A").hasNext() ? s.next() : "";
			s.close();
			return result;
		}
	}

}
