package ch.unibe.scg.doodle.test;

import static org.junit.Assert.assertEquals;

import java.awt.Color;

import org.junit.Before;
import org.junit.Test;

import ch.unibe.scg.doodle.ScratchFactory;
import ch.unibe.scg.doodle.htmlgen.Tag;
import ch.unibe.scg.doodle.inject.DoodleModule;

import com.google.inject.Guice;
import com.google.inject.Injector;

public class HtmlOutputTest {

	private ScratchFactory scratchFactory;

	@Before
	public void init() {
		Injector injector = Guice.createInjector(new DoodleModule());
		scratchFactory = injector.getInstance(ScratchFactory.class);
	}

	@Test
	public void testColorRendering() {
		Tag testTag = new Tag("test");
		scratchFactory.create(Color.RED).drawWhole(testTag);

		assertEquals(
				"<test><div class=\" ColorPlugin\" style=\"background-color:rgba(255,0,0,1.0)\">"
						+ "<div class=\"colorText\">RGB&alpha;: (<span class=\"redPart\">255</span>, "
						+ "<span class=\"greenPart\">0</span>, <span class=\"bluePart\">0</span>, "
						+ "<span class=\"alphaPart\">255</span>)</div>\n</div>\n</test>\n",
				testTag.toString());

	}
}
