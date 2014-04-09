package ch.unibe.scg.doodle.typeTransport;

import java.io.IOException;
import java.util.Iterator;
import java.util.Set;

import ch.unibe.scg.doodle.util.FileUtil;

public class ClassUtil {

	/**
	 * Returns <b>false</b> iff a type is
	 * <ul>
	 * <li>an array</li>
	 * <li>primitive</li>
	 * <li>part of a package in <code>java.*</code></li>
	 * <li>part of a package in <code>javax.*</code></li>
	 * </ul>
	 * 
	 * @param clazz
	 * @return
	 */
	public static boolean isThirdParty(Class<?> clazz) {
		// FIXME: Find a smarter way
		return !clazz.isArray() & !clazz.isPrimitive()
				& !clazz.getName().startsWith("java.")
				& !clazz.getName().startsWith("javax.");
	}

	public static byte[] getBinary(Class<? extends Object> clazz) {
		return FileUtil.readBinaryFile(clazz.getResource(clazz.getSimpleName()
				+ ".class"));
	}

	/**
	 * Finds and returns all non-JDK dependencies (types) of a given type. The
	 * provided type itself is not part of the returned set.
	 * 
	 * @param clazz
	 * @return
	 */
	public static Set<Class<?>> getThirdPartyDependencies(Class<?> clazz) {
		try {
			Set<Class<?>> dependencies = Collector.getClassesUsedBy(
					clazz.getName(), "");
			Iterator<Class<?>> iterator = dependencies.iterator();
			while (iterator.hasNext()) {
				Class<?> dependency = iterator.next();
				if (isThirdParty(dependency))
					dependencies.remove(dependency);
			}
			return dependencies;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

	}

}
