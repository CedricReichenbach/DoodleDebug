package doodle;

import javax.inject.Singleton;

import plugins.ArrayPlugin;
import plugins.ColorPlugin;
import plugins.ImagePlugin;
import plugins.ListPlugin;
import plugins.ObjectPlugin;
import plugins.RenderingPlugin;
import plugins.StringPlugin;
import rendering.ImageRendering;
import rendering.RenderingRegistry;
import rendering.RenderingRegistryProvider;

import com.google.inject.AbstractModule;
import com.google.inject.assistedinject.FactoryModuleBuilder;
import com.google.inject.multibindings.Multibinder;

public class DoodleModule extends AbstractModule {

	@Override
	protected void configure() {
		Multibinder<RenderingPlugin> uriBinder = Multibinder.newSetBinder(binder(), RenderingPlugin.class);
		addPlugins(uriBinder);
		bind(Doodler.class).in(Singleton.class);
		bind(RenderingRegistry.class).toProvider(RenderingRegistryProvider.class);
		install(new FactoryModuleBuilder()
	     .implement(Scratch.class,RealScratch.class)
	     .build(ScratchFactory.class));

	}

	private void addPlugins(Multibinder<RenderingPlugin> uriBinder) {
		uriBinder.addBinding().to(ListPlugin.class);
		uriBinder.addBinding().to(ObjectPlugin.class);
		uriBinder.addBinding().to(ColorPlugin.class);
		uriBinder.addBinding().to(StringPlugin.class);
		uriBinder.addBinding().to(ImagePlugin.class);
	}

}

