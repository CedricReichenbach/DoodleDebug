package doodle;

import plugins.ArrayPlugin;
import plugins.ColorPlugin;
import plugins.ListPlugin;
import plugins.ObjectPlugin;
import plugins.RenderingPlugin;
import rendering.RenderingRegistry;
import rendering.RenderingRegistryProvider;

import com.google.inject.AbstractModule;
import com.google.inject.multibindings.Multibinder;

public class DoodleModule extends AbstractModule {

	@Override
	protected void configure() {
		Multibinder<RenderingPlugin> uriBinder = Multibinder.newSetBinder(binder(), RenderingPlugin.class);
		uriBinder.addBinding().to(ListPlugin.class);
		uriBinder.addBinding().to(ObjectPlugin.class);
		uriBinder.addBinding().to(ArrayPlugin.class); // XXX Array.type makes no sense
		uriBinder.addBinding().to(ColorPlugin.class);
		bind(RenderingRegistry.class).toProvider(RenderingRegistryProvider.class);

	}

}

