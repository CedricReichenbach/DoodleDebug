package ch.unibe.scg.doodle;

import java.io.File;

import ch.unibe.scg.doodle.hbase.HBaseStringMap;
import ch.unibe.scg.doodle.properties.DoodleDebugProperties;
import ch.unibe.scg.doodle.util.ClassUtil;
import ch.unibe.scg.doodle.util.EncodingUtil;
import ch.unibe.scg.doodle.util.FileUtil;

public class ClassManager {

	private final String MAP_NAME = "classbinaries";
	HBaseStringMap classMap = new HBaseStringMap(MAP_NAME);

	public String store(Class<?> clazz) {
		String key = clazz.getName();
		classMap.put(key, EncodingUtil.toBase64(ClassUtil.getBinary(clazz)));
		return key;
	}

	/**
	 * Loads into DD's temp directory for binaries
	 * 
	 * @param fullClassName
	 */
	public void loadToFile(String fullClassName) {
		byte[] binary = EncodingUtil.bytesFromBase64((String) classMap
				.get(fullClassName));
		File file = new File(DoodleDebugProperties.tempDirForClasses(),
				fullClassName.replace(".", "/") + ".class");
		file.getParentFile().mkdirs();
		FileUtil.writeBinaryFile(file, binary);
	}

}
