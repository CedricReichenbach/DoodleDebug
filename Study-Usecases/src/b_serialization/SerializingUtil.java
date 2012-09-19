package b_serialization;

import java.lang.reflect.Field;
import java.util.HashMap;

public class SerializingUtil {
	private static Contact stored;
	private static HashMap<String, Object> data = new HashMap<String, Object>();

	public static void serialize(Contact contact) {
		stored = contact;
		for (Field f : contact.getAddress().getClass().getDeclaredFields()) {
			try {
				if (f.getType().equals(long.class)) {
					Address address = contact.getAddress();
					int num = (int) (f.getLong(address));
					data.put(f.getName(), num);
				}
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
	}

	public static Contact deSerialize() {
		Contact result = stored.getCopy();
		stored = null;
		synchronize(result);
		return result;
	}

	private static void synchronize(Contact result) {
		for (Field f : result.getAddress().getClass().getDeclaredFields()) {
			try {
				if (f.getType().equals(long.class)) {
					result.getAddress()
							.getClass()
							.getDeclaredField(f.getName())
							.setLong(result.getAddress(),
									(long) (int) data.get(f.getName()));
				}
			} catch (NoSuchFieldException | SecurityException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
	}
}
