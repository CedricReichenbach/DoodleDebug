package ch.unibe.scg.doodle.rendering;

import javax.inject.Inject;

import ch.unibe.scg.doodle.Doodler;
import ch.unibe.scg.doodle.htmlgen.Tag;
import ch.unibe.scg.doodle.util.StackTraceUtil;

public class ThrowableRendering implements Rendering<Throwable> {

	@Inject
	Doodler doodler;

	@SuppressWarnings("unchecked")
	@Override
	public void render(Throwable e, Tag tag) throws DoodleRenderException {
		Tag title = new Tag("h3", "class=problemTitle");
		if (e instanceof DoodleRenderException)
			title.add("A problem occurred during rendering. Stack trace:");
		else
			title.add("Stack trace:");
		tag.add(title);
		Tag stackTrace = new Tag("pre", "class=stackTrace");
		String traceString = StackTraceUtil.getStackTraceWithoutCause(e);
		traceString = StackTraceUtil.linkClasses(traceString);
		String replaced = traceString.replace("\n", "<br>");
		stackTrace.add(replaced);
		tag.add(stackTrace);
		if (e.getCause() != null) {
			Tag causeTitle = new Tag("h3", "class=causeTitle");
			causeTitle.add("Cause:");
			tag.add(causeTitle);
			doodler.renderInlineInto(e.getCause(), tag);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void renderSimplified(Throwable e, Tag tag) throws DoodleRenderException {
		tag.add(e.getClass().getSimpleName());
	}

}
