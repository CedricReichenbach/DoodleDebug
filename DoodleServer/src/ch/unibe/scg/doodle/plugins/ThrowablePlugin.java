package ch.unibe.scg.doodle.plugins;

import java.util.HashSet;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Provider;

import ch.unibe.scg.doodle.htmlgen.Tag;
import ch.unibe.scg.doodle.rendering.DoodleRenderException;
import ch.unibe.scg.doodle.rendering.ThrowableRendering;

public class ThrowablePlugin extends AbstractPlugin {

	@Inject
	Provider<ThrowableRendering> throwableRenderingProvider;

	@Override
	public Set<Class<?>> getDrawableClasses() {
		HashSet<Class<?>> hs = new HashSet<Class<?>>();
		hs.add(Throwable.class);
		return hs;
	}
	
	@Override
	public void render(Object object, Tag tag) throws DoodleRenderException {
		throwableRenderingProvider.get().render((Throwable) object, tag);
	}

	@Override
	public void renderSmall(Object object, Tag tag)
			throws DoodleRenderException {
		throwableRenderingProvider.get().renderSmall((Throwable) object, tag);
	}

	@Override
	public String getCSS() {
		String main = ".ThrowablePlugin {background-color: #ff6; color: red; padding: .5em}";
		String mainSmall = ".ThrowablePlugin.smallRendering {padding: 0;}";
		String title = ".ThrowablePlugin .problemTitle {font-weight: 500; margin: 0;}";
		String trace = ".ThrowablePlugin .stackTrace {font-family: monospace, \"Roboto\";}";
		return main + mainSmall + title + trace;
	}

}
