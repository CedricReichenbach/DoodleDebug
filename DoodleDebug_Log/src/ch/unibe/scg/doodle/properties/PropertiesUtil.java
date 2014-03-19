package ch.unibe.scg.doodle.properties;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertiesUtil {
	public static Properties getDoodleDebugProperties() {
		Properties properties = new Properties();
		BufferedInputStream stream;
		try {
			stream = new BufferedInputStream(PropertiesUtil.class.getResource(
					"doodledebug.properties").openStream());
			properties.load(stream);
			stream.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return properties;
	}
}
