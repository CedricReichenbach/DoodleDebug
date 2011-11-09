package doodle;

import java.util.Set;

import plugins.ListPlugin;
import plugins.ObjectPlugin;
import plugins.RenderingPlugin;
import rendering.RenderingRegistry;
import rendering.RenderingRegistryProvider;
import view.DoodleCanvas;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.multibindings.Multibinder;

public class DoodleModule extends AbstractModule {

	@Override
	protected void configure() {
		Multibinder<RenderingPlugin> uriBinder = Multibinder.newSetBinder(binder(), RenderingPlugin.class);
		uriBinder.addBinding().to(ListPlugin.class);
		uriBinder.addBinding().to(ObjectPlugin.class);
		bind(RenderingRegistry.class).toProvider(RenderingRegistryProvider.class);

	}

}

