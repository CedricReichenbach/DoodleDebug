package app_example.ownDrawMethod;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;
import java.util.ArrayList;
import java.util.Scanner;

import doodle.D;

import sun.awt.image.URLImageSource;

public class MainScgDemo {

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		ArrayList<Person> persons = new ArrayList<Person>();
		
		File file = new File(System.getProperty("user.dir")
				+ "/pics/assange.png");
		byte[] image = readFileToByteArray(file);
		Person assange = new Person("Julian Assange",
				new PhoneNumber(35, "312 302 32"), image);
		persons.add(assange);
		
		File file2 = new File(System.getProperty("user.dir")
				+ "/pics/jobs.png");
		byte[] image2 = readFileToByteArray(file2);
		Person jobs = new Person("Steve Jobs",
				new PhoneNumber(1, "800 692 7753"), image2);
		persons.add(jobs);
		
		File file3 = new File(System.getProperty("user.dir")
				+ "/pics/che.png");
		byte[] image3 = readFileToByteArray(file3);
		Person che = new Person("Che Guevara",
				new PhoneNumber(44, "028 240 84"), image3);
		persons.add(che);
		
		System.out.println(persons);
		D.raw(persons);
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
