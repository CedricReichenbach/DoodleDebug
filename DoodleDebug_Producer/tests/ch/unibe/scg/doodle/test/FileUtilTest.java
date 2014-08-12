package ch.unibe.scg.doodle.test;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.net.MalformedURLException;

import org.junit.Before;
import org.junit.Test;

import ch.unibe.scg.doodle.properties.DoodleDebugProperties;
import ch.unibe.scg.doodle.util.FileUtil;

public class FileUtilTest {

	private static final String TEXT = "What a nice day!\n Chätschgummi... Sünneli... ☼";
	private File file;

	@Before
	public void init() {
		file = new File(DoodleDebugProperties.mainTempDir(), "foo.test");
	}

	@Test
	public void testWriteRead() throws MalformedURLException {
		FileUtil.writeToFile(file, TEXT, false);
		String result = FileUtil.readFile(file.toURI().toURL());
		assertEquals(TEXT, result);
	}

	@Test
	public void testReadWriteClassfile() throws MalformedURLException {
		String read = FileUtil.readFile(FileUtilTest.class
				.getResource("FileUtilTest.class"));
		FileUtil.writeToFile(file, read, false);
		String result = FileUtil.readFile(file.toURI().toURL());
		assertEquals(read, result);
	}
}
