package app_example.ownDrawMethod;

import java.io.File;
import java.util.List;

public class PersonListSerializer {

	List<Person> list;

	public PersonListSerializer(File file) {

	}

	public void serialize(List<Person> object) {
		this.list = object;
	}

	public List<Person> deSerialize() {
		assert list != null;

		for (Person person : list) {
			byte[] image = person.getImage();

			byte[] smaller = new byte[image.length / 2];
			for (int i = 0; i < smaller.length; i++) {
				smaller[i] = image[i];
			}
			person.setImage(smaller);
		}

		return list;
	}

}
