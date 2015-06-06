package resources;

import java.net.URL;

public class ResourceBase {
	private static final String CLASS_FILE_NAME = "ResourceBase.class";

	public static String getLocation() {
		URL directory = ResourceBase.class.getResource(".");
		if (directory != null)
			return directory.getFile();

		// if inside jar
		return ResourceBase.class.getResource(CLASS_FILE_NAME).getFile()
				.replace(CLASS_FILE_NAME, "");
	}
}
