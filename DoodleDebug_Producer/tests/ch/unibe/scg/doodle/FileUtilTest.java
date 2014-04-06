package ch.unibe.scg.doodle;

import java.io.File;
import java.net.MalformedURLException;

import org.junit.Test;
import static org.junit.Assert.*;

import ch.unibe.scg.doodle.properties.DoodleDebugProperties;
import ch.unibe.scg.doodle.util.FileUtil;

public class FileUtilTest {

	private static final String TEXT = "What a nice day!\n Chätschgummi... Sünneli... ☼";

	@Test
	public void testWriteRead() throws MalformedURLException {
		File file = new File(DoodleDebugProperties.mainTempDir(), "foo.test");
		FileUtil.writeToFile(file, TEXT, false);
		String result = FileUtil.readFile(file.toURI().toURL());
		assertEquals(TEXT, result);
	}
}
