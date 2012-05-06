package ch.unibe.scg.doodle.inject;

import javax.inject.Singleton;

import ch.unibe.scg.doodle.Doodler;
import ch.unibe.scg.doodle.RealScratch;
import ch.unibe.scg.doodle.Scratch;
import ch.unibe.scg.doodle.ScratchFactory;
import ch.unibe.scg.doodle.plugins.ColorPlugin;
import ch.unibe.scg.doodle.plugins.ImagePlugin;
import ch.unibe.scg.doodle.plugins.ListPlugin;
import ch.unibe.scg.doodle.plugins.NullPlugin;
import ch.unibe.scg.doodle.plugins.ObjectPlugin;
import ch.unibe.scg.doodle.plugins.RenderingPlugin;
import ch.unibe.scg.doodle.plugins.StringPlugin;
import ch.unibe.scg.doodle.rendering.RenderingRegistry;
import ch.unibe.scg.doodle.rendering.RenderingRegistryProvider;

import com.google.inject.AbstractModule;
import com.google.inject.assistedinject.FactoryModuleBuilder;
import com.google.inject.multibindings.Multibinder;

public class DoodleModule extends AbstractModule {

	@Override
	protected void configure() {
		Multibinder<RenderingPlugin> uriBinder = Multibinder.newSetBinder(
				binder(), RenderingPlugin.class);
		addPlugins(uriBinder);
		bind(Doodler.class).in(Singleton.class);
		bind(RenderingRegistry.class).toProvider(
				RenderingRegistryProvider.class);
		install(new FactoryModuleBuilder().implement(Scratch.class,
				RealScratch.class).build(ScratchFactory.class));

	}

	private void addPlugins(Multibinder<RenderingPlugin> uriBinder) {
		uriBinder.addBinding().to(ListPlugin.class);
		uriBinder.addBinding().to(ObjectPlugin.class);
		uriBinder.addBinding().to(ColorPlugin.class);
		uriBinder.addBinding().to(StringPlugin.class);
		uriBinder.addBinding().to(ImagePlugin.class);
		uriBinder.addBinding().to(NullPlugin.class);
	}

}
