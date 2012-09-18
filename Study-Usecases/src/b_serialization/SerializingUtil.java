package b_serialization;

public class SerializingUtil {
	private static Contact stored;

	public static void serialize(Contact contact) {
		stored = contact;
	}

	public static Contact deSerialize() {
		Contact result = stored.getCopy();
		stored = null;
		destroy(result);
		return result;
	}

	private static void destroy(Contact result) {
		Address a = result.getAddress();
		a.postalCode *= 2;
		result.setAddress(result.getAddress());
	}
}
