package ch.unibe.scg.doodle.util;

public class ClassUtil {

	public static boolean isThirdParty(Class<?> clazz) {
		// FIXME: Find a smarter way
		return !clazz.isArray() & !clazz.isPrimitive()
				& !clazz.getName().startsWith("java.");
	}

	public static byte[] getBinary(Class<? extends Object> clazz) {
		return FileUtil.readBinaryFile(clazz.getResource(clazz.getSimpleName()
				+ ".class"));
	}

}
