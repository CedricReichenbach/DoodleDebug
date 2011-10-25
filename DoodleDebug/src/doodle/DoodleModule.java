package doodle;

import plugins.Plugin;

import com.google.inject.AbstractModule;
import com.google.inject.multibindings.Multibinder;

public class DoodleModule extends AbstractModule {

	@Override
	protected void configure() {
		Multibinder<Plugin> uriBinder = Multibinder.newSetBinder(binder(), Plugin.class);
		uriBinder.addBinding().to(ArrayPlugin.class);


	}

}
