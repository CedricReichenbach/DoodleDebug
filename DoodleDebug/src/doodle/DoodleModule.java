package doodle;

import plugins.ListPlugin;
import plugins.Plugin;
import rendering.RenderingRegistry;
import rendering.RenderingRegistryProvider;

import com.google.inject.AbstractModule;
import com.google.inject.multibindings.Multibinder;

public class DoodleModule extends AbstractModule {

	@Override
	protected void configure() {
		Multibinder<Plugin> uriBinder = Multibinder.newSetBinder(binder(), Plugin.class);
		uriBinder.addBinding().to(ListPlugin.class);
		bind(RenderingRegistry.class).toProvider(RenderingRegistryProvider.class);

	}

}
