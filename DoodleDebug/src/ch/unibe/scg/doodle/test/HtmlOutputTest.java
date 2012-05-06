package ch.unibe.scg.doodle.test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import java.awt.Color;

import org.junit.Before;
import org.junit.Test;

import ch.unibe.ch.scg.htmlgen.Tag;
import ch.unibe.scg.doodle.ScratchFactory;
import ch.unibe.scg.doodle.inject.DoodleModule;

import com.google.inject.Guice;
import com.google.inject.Injector;


public class HtmlOutputTest {
	
	private ScratchFactory scratchFactory;

	@Before
	public void init() {
		Injector injector = Guice.createInjector(new DoodleModule());
		scratchFactory = injector
				.getInstance(ScratchFactory.class);
	}

	@Test
	public void testColorRendering() {
		Tag testTag = new Tag("test");
		scratchFactory.create(Color.RED).drawWhole(testTag);

		assertThat(
				testTag.toString(),
				is("<test style=\"width:100; background-color:#ff0000\">java.awt.Color[r=255,g=0,b=0]</test>\n"));
	}
}
