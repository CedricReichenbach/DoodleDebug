package ch.unibe.scg.doodle;

import java.net.URL;
import java.util.List;

import javax.inject.Inject;

import org.eclipse.swt.widgets.Display;

import ch.unibe.scg.doodle.helperClasses.CannotRenderMessage;
import ch.unibe.scg.doodle.helperClasses.NullObject;
import ch.unibe.scg.doodle.htmlgen.Attribute;
import ch.unibe.scg.doodle.htmlgen.Tag;
import ch.unibe.scg.doodle.properties.DoodleDebugProperties;
import ch.unibe.scg.doodle.server.DoodleServer;
import ch.unibe.scg.doodle.server.LightboxStack;
import ch.unibe.scg.doodle.server.util.DoodleImages;
import ch.unibe.scg.doodle.server.views.HtmlShow;
import ch.unibe.scg.doodle.server.views.JavascriptExecuter;
import ch.unibe.scg.doodle.util.JavascriptCallsUtil;
import ch.unibe.scg.doodle.view.CSSCollection;
import ch.unibe.scg.doodle.view.HtmlDocument;

/**
 * Class used for visualizing any Object
 * 
 * @author Cedric Reichenbach
 * 
 */
public class Doodler {

	@Inject
	ScratchFactory scratchFactory;
	@Inject
	SmallScratchFactory smallScratchFactory;

	private Tag body;

	private int level;

	private IndexedObjectStorage clickables;

	private static Doodler instance; // XXX Sorry, Guice...

	/**
	 * Creates a new Doodler for visualizing objects 1 Doodler = 1 window
	 */
	@SuppressWarnings("unchecked")
	protected Doodler() {
		HtmlDocument htmlDocument = new HtmlDocument();
		body = new Tag("body");
		htmlDocument.setBody(body);

		setBackgroundImage();

		if (DoodleDebugProperties.betaMode())
			this.createBetaInfo(body);

		prepareLightbox();

		Runnable htmlShow = new HtmlShow(htmlDocument.toString());
		Display.getDefault().syncExec(htmlShow);

		clickables = new IndexedObjectStorage();
		instance = this;
	}

	public static Doodler instance() {
		return instance;
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
	 * Visualizes any Object, either using draw Method of the object itself (if
	 * existing) or does a default drawing.
	 * 
	 * @param Object
	 * 
	 */
	@SuppressWarnings("unchecked")
	void visualize(Object o) {
		if (o == null) {
			o = new NullObject();
		}

		Scratch scratch = scratchFactory.create(o);
		scratch.addCSSClass("printOut");
		this.level = 0;

		visualizeScratch(scratch);
	}

	void visualizeScratch(Scratch scratch) {
		Tag printOutWrapper = new Tag("div", "class=printOutWrapper");
		printOutWrapper.addAttribute(new Attribute("class", "rendering"));
		scratch.drawWholeWithName(printOutWrapper);

		DoodleServer.instance().setStorage(clickables);

		String css = CSSCollection.flushAllCSS();
		Runnable jsExecuterForCSS = new JavascriptExecuter(
				JavascriptCallsUtil.addCSS(css));
		Display.getDefault().syncExec(jsExecuterForCSS);

		Runnable jsExecuterForHtml = new JavascriptExecuter(
				JavascriptCallsUtil.addToBodyCall(printOutWrapper.toString()));
		Display.getDefault().syncExec(jsExecuterForHtml);
	}

	/**
	 * Sub-render any object into a given tag.<br>
	 * This method may be called from draw()/drawSmall() methods inside client
	 * RenderingPlugins.
	 * 
	 * @param object
	 * @param tag
	 */
	public void renderInlineInto(Object object, Tag tag) {
		renderInline(object, tag, true, false);
	}

	/**
	 * Sub-render any object into a given tag without rendering its class name.<br>
	 * This method may be called from draw()/drawSmall() methods inside client
	 * RenderingPlugins.
	 * 
	 * @param object
	 * @param tag
	 */
	public void renderInlineIntoWithoutClassName(Object object, Tag tag) {
		renderInline(object, tag, false, false);
	}

	public void renderSmallInlineInto(Object object, Tag tag) {
		renderInline(object, tag, false, true);
	}

	/**
	 * Render an object into lightbox.<br>
	 * This method should not be used by clients.
	 * 
	 * @param stack
	 * @param tag
	 */
	public synchronized void renderIntoLightbox(LightboxStack stack, Tag tag) {
		level--;
		renderBreadcrumbs(stack, tag);
		Tag lightboxRendering = new Tag("div", "id=lightboxRendering");
		renderInline(stack.top(), lightboxRendering, true, false);
		tag.add(lightboxRendering);
		level++;
	}

	private synchronized void renderInline(Object object, Tag tag,
			boolean withClassName, boolean small) {
		if (object == null) {
			object = new NullObject();
		}
		level++; // TODO: smarter, nicer solution for this
		tag.addCSSClass("rendering");
		Scratch scratch = null;
		if (level <= 1 && !small) {
			scratch = scratchFactory.create(object);
		} else {
			scratch = smallScratchFactory.create(object);
		}
		scratch.addCSSClass("level" + level);
		scratch.setLevel(level);
		if (level == 1)
			scratch.setObjectID(clickables.store(object));
		if (withClassName) {
			scratch.drawWholeWithName(tag);
		} else {
			scratch.drawWhole(tag);
		}
		level--;
	}

	private void renderBreadcrumbs(LightboxStack stack, Tag tag) {
		Tag breadcrumbs = new Tag("div", "id=breadcrumbs");
		List<Object> objects = stack.bottomUpList();
		int depth = objects.size() - 1;

		// in front of breadcrumbs
		Tag home = new Tag("div", "id=dd-home");
		String homeImgPath = DoodleImages.getDDIconPath();
		Tag homeImg = new Tag("img", "src=" + homeImgPath);
		homeImg.addAttribute("onclick","javascript:hideLightbox()");
		home.add(homeImg);
		breadcrumbs.add(home);
		Tag before = new Tag("div", "class=betweenBreadcrumbs");
		before.add("&#x25B6;"); // Unicode: BLACK RIGHT-POINTING TRIANGLE
		breadcrumbs.add(before);

		for (int i = 0; i < objects.size() - 1; i++) {
			Object o = objects.get(i);
			Tag breadcrumb = new Tag("div", "class=breadcrumb");
			breadcrumb.addCSSClass("inactiveBreadcrumb");
			breadcrumb.addAttribute("onclick", "javascript:breadcrumbsBack("
					+ depth + ")");
			String name = scratchFactory.create(o).getObjectTypeName();
			breadcrumb.add(name);
			breadcrumbs.add(breadcrumb);
			Tag between = new Tag("div", "class=betweenBreadcrumbs");
			between.add("&#x25B6;"); // Unicode: BLACK RIGHT-POINTING TRIANGLE
			breadcrumbs.add(between);
			depth--;
		}
		if (!objects.isEmpty()) { // handle last one
			Object o = objects.get(objects.size() - 1);
			Tag breadcrumb = new Tag("div", "class=breadcrumb");
			breadcrumb.addAttribute("id", "activeBreadcrumb"); // last one
			String name = scratchFactory.create(o).getObjectTypeName();
			if (o instanceof NullObject)
				name = "?";
			breadcrumb.add(name);
			breadcrumbs.add(breadcrumb);
		}
		Tag breadcrumbsWrapper = new Tag("div", "id=breadcrumbsWrapper");
		breadcrumbsWrapper.add(breadcrumbs);
		tag.add(breadcrumbsWrapper);
	}

	/**
	 * Display an error message when problems occur, e.g. when sending. <br>
	 * Clients may not use this method, but throw a
	 * {@link ch.unibe.scg.doodle.rendering.DoodleRenderException
	 * DoodleRenderException}.
	 * 
	 * @param canonicalName
	 */
	public void couldNotRenderMessage(String canonicalName) {
		CannotRenderMessage messageObject = new CannotRenderMessage(
				canonicalName);
		Scratch scratch = scratchFactory.create(messageObject);
		scratch.addCSSClass("couldNotRenderMessage");
		scratch.addCSSClass("printOut");
		this.visualizeScratch(scratch);
	}
}