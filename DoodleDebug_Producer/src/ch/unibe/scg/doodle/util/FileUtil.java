package ch.unibe.scg.doodle.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class FileUtil {

	public static String readFile(URL url) {
		String result = "";

		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(
					url.openStream()));
			String inputLine = in.readLine();
			while (inputLine != null) {
				result += inputLine;
				inputLine = in.readLine();
				if (inputLine != null)
					result += "\n";
			}
			in.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		return result;
	}

	public static void writeToFile(File file, String content, boolean append) {
		try {
			FileWriter fstream = new FileWriter(file, append);
			BufferedWriter out = new BufferedWriter(fstream);
			out.write(content);
			out.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
