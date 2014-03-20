package ch.unibe.scg.doodle.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Vector;

import org.junit.Test;

import ch.unibe.scg.doodle.inject.DoodleModule;
import ch.unibe.scg.doodle.plugins.CollectionPlugin;
import ch.unibe.scg.doodle.rendering.RenderingRegistry;

import com.google.inject.Guice;
import com.google.inject.Injector;

public class PluginBindingTest {

	@Test
	public void testDefaultFound() {
		Injector injector = Guice.createInjector(new DoodleModule());
		RenderingRegistry registry = injector
				.getInstance(RenderingRegistry.class);
		// assertThat(registry.lookup(Vector.class),
		// isA(CollectionPlugin.class));
		// XXX: Why isn't above version working?
		assertTrue(registry.lookup(Vector.class) instanceof CollectionPlugin);
	}

	@Test
	public void testLeveling() {
		assertEquals(
				RenderingRegistry.superTypesLevelwise(Integer.class).toString(),
				"[[class java.lang.Integer], [interface java.lang.Comparable, "
						+ "class java.lang.Number], [interface java.io.Serializable, "
						+ "class java.lang.Object]]");

		assertEquals(
				RenderingRegistry.superTypesLevelwise(Vector.class).toString(),
				"[[class java.util.Vector], [interface java.util.List, interface"
						+ " java.util.RandomAccess, interface java.lang.Cloneable,"
						+ " interface java.io.Serializable, class java.util.AbstractList], "
						+ "[interface java.util.Collection, interface java.util.List,"
						+ " class java.util.AbstractCollection], [interface java.lang.Iterable,"
						+ " interface java.util.Collection, interface java.util.Collection, class"
						+ " java.lang.Object], [interface java.lang.Iterable, interface java.lang.Iterable]]");
	}

}
