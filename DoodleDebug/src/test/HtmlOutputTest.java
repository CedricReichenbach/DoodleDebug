package test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import html_generator.Tag;

import java.awt.Color;

import org.junit.Before;
import org.junit.Test;

import com.google.inject.Guice;
import com.google.inject.Injector;

import doodle.DoodleModule;
import doodle.ScratchFactory;

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
