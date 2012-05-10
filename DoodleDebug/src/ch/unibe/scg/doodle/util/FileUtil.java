package ch.unibe.scg.doodle.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class FileUtil {

	public static String readFile(URL url) {
		String result = "";

		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(
					url.openStream()));
			String inputLine = "";
			while (inputLine != null) {
				result += inputLine + "\n";
				inputLine = in.readLine();
			}
			in.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		return result;
	}
}
