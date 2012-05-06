package ch.unibe.scg.doodle.plugins;

import java.util.HashSet;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Provider;

import ch.unibe.ch.scg.htmlgen.Tag;
import ch.unibe.scg.doodle.helperClasses.NullObject;
import ch.unibe.scg.doodle.rendering.NullRendering;

public class NullPlugin extends AbstractPlugin {

	@Inject
	Provider<NullRendering> nullRenderingProvider;

	@Override
	public Set<Class<?>> getDrawableClasses() {
		HashSet<Class<?>> set = new HashSet<Class<?>>();
		set.add(NullObject.class);
		return set;
	}

	@Override
	public String getObjectTypeName(Object o) {
		return "null";
	}

	@Override
	public void render(Object object, Tag tag) {
		nullRenderingProvider.get().render((NullObject) object, tag);
	}

}
