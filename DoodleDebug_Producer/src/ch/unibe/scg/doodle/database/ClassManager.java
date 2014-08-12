package ch.unibe.scg.doodle.database;

import java.io.File;

import ch.unibe.scg.doodle.properties.DoodleDebugProperties;
import ch.unibe.scg.doodle.typeTransport.ClassUtil;
import ch.unibe.scg.doodle.util.ApplicationUtil;
import ch.unibe.scg.doodle.util.EncodingUtil;
import ch.unibe.scg.doodle.util.FileUtil;

public class ClassManager {

	private static final String MAP_NAME = "classbinaries";
	static DoodleDatabaseMap<String> classMap = DoodleDatabaseMapFactory.get(
			ApplicationUtil.getApplicationName(), MAP_NAME);

	public static String store(Class<?> clazz) {
		String key = clazz.getName();
		classMap.put(key, EncodingUtil.toBase64(ClassUtil.getBinary(clazz)));
		return key;
	}

	/**
	 * Loads into DD's temp directory for binaries
	 * 
	 * @param fullClassName
	 */
	public static void loadToFile(String fullClassName) {
		byte[] binary = EncodingUtil.bytesFromBase64((String) classMap
				.get(fullClassName));
		File file = new File(DoodleDebugProperties.tempDirForClasses(),
				fullClassName.replace(".", "/") + ".class");
		file.getParentFile().mkdirs();
		FileUtil.writeBinaryFile(file, binary);
	}

}
