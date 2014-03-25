package ch.unibe.scg.doodle;

import javax.inject.Inject;

import ch.unibe.scg.doodle.hbase.DoodleDatabase;
import ch.unibe.scg.doodle.hbase.HBaseMap;
import ch.unibe.scg.doodle.helperClasses.CannotRenderMessage;
import ch.unibe.scg.doodle.helperClasses.NullObject;
import ch.unibe.scg.doodle.htmlgen.Attribute;
import ch.unibe.scg.doodle.htmlgen.Tag;
import ch.unibe.scg.doodle.inject.DoodleModule;
import ch.unibe.scg.doodle.util.JavascriptCallsUtil;
import ch.unibe.scg.doodle.view.CSSCollection;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.thoughtworks.xstream.XStream;

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

	private static final String TABLE_NAME = "objects";
	private final HBaseMap hbaseMap = new HBaseMap(TABLE_NAME);
	private final XStream xstream = new XStream();
	private final IndexedObjectStorage storage = new IndexedObjectStorage();

	int level = 0;

	DoodleDatabase doodleDatabase = new DoodleDatabase();

	static Injector injector;

	static Injector injectorInstance() {
		if (injector == null) {
			injector = Guice.createInjector(new DoodleModule());
		}
		return injector;
	}

	public static void resetInjector() {
		injector = null;
	}

	/**
	 * Creates a new Doodler for visualizing objects 1 Doodler = 1 window
	 */
	protected Doodler() {
	}

	public static Doodler instance() {
		return injectorInstance().getInstance(Doodler.class);
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

		visualizeScratch(scratch);
	}

	void visualizeScratch(Scratch scratch) {
		Tag printOutWrapper = new Tag("div", "class=printOutWrapper");
		printOutWrapper.addAttribute(new Attribute("class", "rendering"));
		scratch.drawWholeWithName(printOutWrapper);

		String css = CSSCollection.flushAllCSS();
		String cssAddingScript = JavascriptCallsUtil.addCSS(css);

		String htmlAddingScript = JavascriptCallsUtil
				.addToBodyCall(printOutWrapper.toString());

		doodleDatabase.store(htmlAddingScript, cssAddingScript);
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

	synchronized void renderInline(Object object, Tag tag,
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
			scratch.setObjectID(storage.store(object));
		if (withClassName) {
			scratch.drawWholeWithName(tag);
		} else {
			scratch.drawWhole(tag);
		}
		level--;
	}

}