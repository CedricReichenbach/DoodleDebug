package ch.unibe.scg.doodle;

import javax.inject.Inject;

import ch.unibe.scg.doodle.database.ImageManager;
import ch.unibe.scg.doodle.htmlgen.Tag;
import ch.unibe.scg.doodle.inject.DoodleModule;
import ch.unibe.scg.doodle.jetty.websocket.WebSocketUtil;
import ch.unibe.scg.doodle.properties.DoodleDebugProperties;
import ch.unibe.scg.doodle.server.LightboxStack;
import ch.unibe.scg.doodle.server.util.DoodleImages;
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

	private Tag body;

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
		body = new Tag("body");
		htmlDocument.setBody(body);

		setBackgroundImage();

		if (DoodleDebugProperties.betaMode())
			this.createBetaInfo(body);

		prepareLightbox();

		return htmlDocument.toString();
	}

	private void setBackgroundImage() {
		String texPath = DoodleImages.getDoodleTextureImageFilePath();
		body.addAttribute("style", "background-image:url(" + texPath + ")");
	}

	@SuppressWarnings("unchecked")
	private void createBetaInfo(Tag tag) {
		Tag wrapper = new Tag("div", "id=betaInfoWrapper");
		Tag info = new Tag("div", "id=betaInfo");
		String email = DoodleDebugProperties.getFeedbackMailAddress();
		Tag mailto = new Tag("a", "href=mailto:" + email);
		mailto.add(email);
		info.add("<b>DoodleDebug <i>beta</i></b> - bugs & feedback to ");
		info.add(mailto);
		wrapper.add(info);
		tag.add(wrapper);
	}

	@SuppressWarnings("unchecked")
	private void prepareLightbox() {
		Tag lighboxWrapper = new Tag("div", "id=lightboxWrapper");
		lighboxWrapper.addAttribute("style", "visibility:hidden");
		Tag overlay = new Tag("div", "id=overlay");
		overlay.addAttribute("onclick", "javascript:hideLightbox()");
		lighboxWrapper.add(overlay);
		Tag lightbox = new Tag("div", "id=lightbox");
		lighboxWrapper.add(lightbox);
		String closeImgFilePath = DoodleImages.getCloseWindowImageFilePath();
		Tag closeImg = new Tag("img", "id=closeButton");
		closeImg.addAttribute("src", closeImgFilePath);
		closeImg.addAttribute("onclick", "javascript:hideLightbox()");
		lighboxWrapper.add(closeImg);
		body.add(lighboxWrapper);
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
	public void loadImage(int index) {
		String base64 = imageManager.load(index);
		WebSocketUtil.executeJavascript(JavascriptCallsUtil.insertImgSrc(index,
				base64));
	}
}
