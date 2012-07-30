package app_example.ownDrawMethod;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import ch.unibe.scg.doodle.Doo;


public class MainScgDemo {

	/**
	 * @param args
	 * @throws IOException
	 * @throws ClassNotFoundException 
	 */
	public static void main(String[] args) throws IOException, ClassNotFoundException {
		ArrayList<Person> persons = new ArrayList<Person>();
		makeAddressBook(persons);

		File file = File.createTempFile("persons", ".txt");
		PersonListSerializer serializer = new PersonListSerializer(file);

		System.out.println(persons);

		serializer.serialize(persons);

		List<Person> list = serializer.deSerialize();
		
		System.out.println(list);
	}
	

	@Test
	public void testSerializer() 
			throws IOException, ClassNotFoundException {
		ArrayList<Person> beforeSerializing = 
				new ArrayList<Person>();
		makeAddressBook(beforeSerializing);

		File file = File.createTempFile("persons", ".txt");
		PersonListSerializer serializer = 
				new PersonListSerializer(file);


		serializer.serialize(beforeSerializing);

		List<Person> afterSerializing = serializer.deSerialize();
		
		System.out.println(beforeSerializing);
		System.out.println(afterSerializing);
		
		Doo.dle(beforeSerializing);
		Doo.dle(afterSerializing);
		
		assertEquals(beforeSerializing,afterSerializing);
	}

	static void makeAddressBook(ArrayList<Person> persons) throws IOException {
		Person assange = new Person("Julian Assange", new PhoneNumber(35,
				"312 302 32"), loadImage("assange.png"));
		persons.add(assange);

		Person jobs = new Person("Steve Jobs", new PhoneNumber(1,
				"800 692 7753"), loadImage("jobs.png"));
		persons.add(jobs);

		Person che = new Person("Che Guevara",
				new PhoneNumber(44, "028 240 84"), loadImage("che.png"));
		persons.add(che);
	}

	static byte[] loadImage(String name) throws IOException {
		File file = new File(System.getProperty("user.dir") + "/pics/" + name);
		byte[] image = readFileToByteArray(file);
		return image;
	}

	private static byte[] readFileToByteArray(File file) throws IOException {
		FileInputStream fin = null;
		FileChannel ch = null;
		try {
			fin = new FileInputStream(file);
			ch = fin.getChannel();
			int size = (int) ch.size();
			MappedByteBuffer buf = ch.map(MapMode.READ_ONLY, 0, size);
			byte[] bytes = new byte[size];
			buf.get(bytes);
			return bytes;
		} finally {
			if (fin != null) {
				fin.close();
			}
			if (ch != null) {
				ch.close();
			}
		}

	}

}
