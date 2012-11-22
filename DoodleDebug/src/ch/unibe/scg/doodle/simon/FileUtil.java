package ch.unibe.scg.doodle.simon;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class FileUtil {
	static File mainTempDir() {
		File dir = new File(tempDir());
		dir.mkdirs();
		return dir;
	}

	private static String tempDir() {
		return System.getProperty("java.io.tmpdir") + "/doodleDebug";
	}

	public static String readFromFile(File file) {
		String result = "";

		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(file
					.toURI().toURL().openStream()));
			String inputLine = in.readLine();
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
