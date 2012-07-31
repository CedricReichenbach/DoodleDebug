package ch.unibe.scg.doodle.rendering;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

import ch.unibe.scg.doodle.htmlgen.Tag;

public class ThrowableRendering implements Rendering<Throwable> {

	@SuppressWarnings("unchecked")
	@Override
	public void render(Throwable e, Tag tag) throws DoodleRenderException {
		Tag title = new Tag("h3", "class=problemTitle");
		if (e instanceof DoodleRenderException)
			title.add("A problem occurred during rendering. Stack trace:");
		else
			title.add("Stack trace:");
		tag.add(title);
		Tag stackTrace = new Tag("p", "class=stackTrace");
		String traceString = getStackTrace(e);
		String replaced = traceString.replace("\n", "<br>");
		stackTrace.add(replaced);
		tag.add(stackTrace);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void renderSmall(Throwable e, Tag tag) throws DoodleRenderException {
		tag.add(e.getClass().getSimpleName());
	}

	public static String getStackTrace(Throwable throwable) {
		Writer writer = new StringWriter();
		PrintWriter printWriter = new PrintWriter(writer);
		throwable.printStackTrace(printWriter);
		return writer.toString();
	}
}
