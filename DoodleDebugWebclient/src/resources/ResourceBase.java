package resources;

public class ResourceBase {
	public static String getLocation() {
		return ResourceBase.class.getResource(".").getFile();
	}
}
