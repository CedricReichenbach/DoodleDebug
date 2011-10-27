package rendering;

import org.junit.Test;

import plugins.ListPlugin;


import com.google.inject.Guice;
import com.google.inject.Injector;
import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;

import static org.junit.Assert.*;
import static org.hamcrest.core.Is.*;
import static org.hamcrest.core.IsInstanceOf.*;
import doodle.*;

public class PluginBindingTest {
	
	@Test
	public  void testDefaultFound() {
		Injector injector = Guice.createInjector(new DoodleModule());
		RenderingRegistry registry = injector.getInstance(RenderingRegistry.class);
		assertThat(registry.lookup(Array.class), is((Object) ListPlugin.class));
	}
}
