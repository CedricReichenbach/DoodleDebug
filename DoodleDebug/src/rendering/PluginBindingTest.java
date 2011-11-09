package rendering;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.hamcrest.Matcher;
import org.junit.Test;

import plugins.ListPlugin;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;

import static org.junit.Assert.*;
import static org.hamcrest.core.Is.*;
import static org.hamcrest.core.IsInstanceOf.*;
import static org.junit.matchers.JUnitMatchers.*;

import doodle.*;

public class PluginBindingTest {

	@Test
	public void testDefaultFound() {
		Injector injector = Guice.createInjector(new DoodleModule());
		RenderingRegistry registry = injector
				.getInstance(RenderingRegistry.class);
		assertThat(registry.lookup(Vector.class), is(ListPlugin.class));
	}

	@Test
	public void testLeveling() {
		assertThat(
				RenderingRegistry.superTypesLevelwise(Integer.class).toString(),
				is("[[class java.lang.Integer], [interface java.lang.Comparable, "
						+ "class java.lang.Number], [interface java.io.Serializable, "
						+ "class java.lang.Object]]"));

		assertThat(
				RenderingRegistry.superTypesLevelwise(Vector.class).toString(),
				is("[[class java.util.Vector], [interface java.util.List, interface"
						+ " java.util.RandomAccess, interface java.lang.Cloneable,"
						+ " interface java.io.Serializable, class java.util.AbstractList], "
						+ "[interface java.util.Collection, interface java.util.List,"
						+ " class java.util.AbstractCollection], [interface java.lang.Iterable,"
						+ " interface java.util.Collection, interface java.util.Collection, class"
						+ " java.lang.Object], [interface java.lang.Iterable, interface java.lang.Iterable]]"));
	}

}


