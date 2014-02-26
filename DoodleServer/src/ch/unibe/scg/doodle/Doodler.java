package ch.unibe.scg.doodle;

import javax.inject.Inject;

import org.eclipse.swt.widgets.Display;

import com.thoughtworks.xstream.XStream;

import ch.unibe.scg.doodle.hbase.DoodleDatabase;
import ch.unibe.scg.doodle.hbase.HBaseMap;
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
import ch.unibe.scg.doodle.util.BreadcrumbsBuilder;
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

	@Inject
	BreadcrumbsBuilder breadcrumbsBuilder;

	private Tag body;

	private int level;

	private static Doodler instance; // XXX Sorry, Guice...

	private static final String TABLE_NAME = "objects";
	private final HBaseMap hbaseMap = new HBaseMap(TABLE_NAME);
	private final XStream xstream = new XStream();
	
	DoodleDatabase doodleDatabase = new DoodleDatabase();

	/**
	 * Creates a new Doodler for visualizing objects 1 Doodler = 1 window
	 */
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
	 * Visualizes any Object, either using draw Method of the object itself (if
	 * existing) or does a default drawing.
	 * 
	 * @param Object
	 * 
	 */
	void visualize(Object o) {
		if (o == null) {
			o = new NullObject();
		}

		hbaseMap.put(o.hashCode(), xstream.toXML(o));

		Scratch scratch = scratchFactory.create(o);
		scratch.addCSSClass("printOut");
		this.level = 0;

		visualizeScratch(scratch);
	}

	void visualizeScratch(Scratch scratch) {
		Tag printOutWrapper = new Tag("div", "class=printOutWrapper");
		printOutWrapper.addAttribute(new Attribute("class", "rendering"));
		scratch.drawWholeWithName(printOutWrapper);

		String css = CSSCollection.flushAllCSS();
		String cssAddingScript = JavascriptCallsUtil.addCSS(css);
		Runnable jsExecuterForCSS = new JavascriptExecuter(cssAddingScript);
		Display.getDefault().syncExec(jsExecuterForCSS);

		String htmlAddingScript = JavascriptCallsUtil
				.addToBodyCall(printOutWrapper.toString());
		Runnable jsExecuterForHtml = new JavascriptExecuter(htmlAddingScript);
		Display.getDefault().syncExec(jsExecuterForHtml);
		
		doodleDatabase.store(htmlAddingScript, cssAddingScript);
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
	@SuppressWarnings("unchecked")
	public synchronized void renderIntoLightbox(LightboxStack stack, Tag tag) {
		level--;
		breadcrumbsBuilder.renderBreadcrumbs(stack, tag);
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
			scratch.setObjectID(DoodleServer.instance().store(object));
		if (withClassName) {
			scratch.drawWholeWithName(tag);
		} else {
			scratch.drawWhole(tag);
		}
		level--;
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