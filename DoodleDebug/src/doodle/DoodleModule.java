package doodle;

import plugins.ListPlugin;
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
		bind(RenderingRegistry.class).toProvider(RenderingRegistryProvider.class);

	}

}
