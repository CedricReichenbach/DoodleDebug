package ch.unibe.scg.doodle;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import ch.unibe.scg.doodle.hbase.HBaseStringMap;
import ch.unibe.scg.doodle.properties.DoodleDebugProperties;
import ch.unibe.scg.doodle.util.ClassUtil;
import ch.unibe.scg.doodle.util.FileUtil;

public class ClassManager {

	private final String MAP_NAME = "classbinaries";
	HBaseStringMap classMap = new HBaseStringMap(MAP_NAME);

	public String store(Class<?> clazz) {
		String key = clazz.getName();
		classMap.put(key, ClassUtil.getBinary(clazz));
		return key;
	}

	public URL loadToFile(String className) {
		String binary = (String) classMap.get(className);
		File file = new File(DoodleDebugProperties.mainTempDir(), className
				+ ".class");
		file.getParentFile().mkdirs();
		FileUtil.writeToFile(file, binary);
		try {
			return file.toURI().toURL(); // FIXME: Only parent one should be enough
		} catch (MalformedURLException e) {
			throw new RuntimeException(e);
		}
	}

}
