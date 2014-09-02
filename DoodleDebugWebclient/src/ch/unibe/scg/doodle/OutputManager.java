package ch.unibe.scg.doodle;

import javax.inject.Inject;

import ch.unibe.scg.doodle.database.ImageManager;
import ch.unibe.scg.doodle.htmlgen.Tag;
import ch.unibe.scg.doodle.inject.DoodleModule;
import ch.unibe.scg.doodle.server.LightboxStack;
import ch.unibe.scg.doodle.util.BreadcrumbsBuilder;
import ch.unibe.scg.doodle.util.JavascriptCallsUtil;
import ch.unibe.scg.doodle.view.HtmlDocument;

import com.google.inject.Guice;
import com.google.inject.Injector;

/**
 * This object initializes and the output view, prints doodles and handles
 * lightboxes.
 * 
 * Previously (in non-clustered Eclipse plugin), this was done by
 * {@link ch.unibe.scg.doodle.Doodler Doodler}.
 * 
 * @author cedric
 * 
 */
public class OutputManager {

	@Inject
	ScratchFactory scratchFactory;
	@Inject
	SmallScratchFactory smallScratchFactory;

	@Inject
	BreadcrumbsBuilder breadcrumbsBuilder;

	@Inject
	ImageManager imageManager;

	Doodler doodler = Doodler.instance();

	static Injector injector;

	private static Injector injectorInstance() {
		if (injector == null) {
			injector = Guice.createInjector(new DoodleModule());
		}
		return injector;
	}

	private static OutputManager instance;

	// XXX There was some strange behaviour with injector only
	public static OutputManager instance() {
		if (instance == null)
			instance = init();
		return instance;
	}

	private static OutputManager init() {
		DoodleDebugConfig.setReadingMode(true);
		return injectorInstance().getInstance(OutputManager.class);
	}

	public static void resetInstance() {
		instance = null;
	}

	protected OutputManager() {
	}

	public String initOutput() {
		HtmlDocument htmlDocument = new HtmlDocument();

		return htmlDocument.toString();
	}

	/**
	 * Render an object into lightbox.<br>
	 * This method should not be used by clients.
	 * 
	 * @param stack
	 * @param tag
	 */
	@SuppressWarnings("unchecked")
	public synchronized void renderIntoLightbox(LightboxStack stack, Tag tag) {
		doodler.level--;
		breadcrumbsBuilder.renderBreadcrumbs(stack, tag);
		Tag lightboxRendering = new Tag("div", "id=lightboxRendering");
		doodler.renderInline(stack.top(), lightboxRendering, true, false);
		tag.add(lightboxRendering);
		doodler.level++;
	}

	// XXX: Does this belong here?
	/**
	 * 
	 * @param index
	 * @return Javascript to execute on client
	 */
	public String loadImage(int index) {
		String base64 = imageManager.load(index);
		return JavascriptCallsUtil.insertImgSrc(index, base64);
	}
}
