package ch.unibe.scg.doodle;

import javax.inject.Inject;

import ch.unibe.scg.doodle.api.Doodleable;
import ch.unibe.scg.doodle.helperClasses.ErrorDrawer;
import ch.unibe.scg.doodle.htmlgen.Tag;
import ch.unibe.scg.doodle.plugins.RenderingPlugin;
import ch.unibe.scg.doodle.rendering.DoodleRenderException;

import com.google.inject.assistedinject.Assisted;

public class SmallScratch extends RealScratch {

	/**
	 * Creates a new SmallScratch for visualizing objects
	 */
	@Inject
	SmallScratch(@Assisted Object o) {
		super(o);
	}

	@Override
	protected void drawDoodleable() {
		try {
			((Doodleable) object).summarizeOn(this.canvas);
		} catch (Exception e) {
			ErrorDrawer errorDrawer = new ErrorDrawer(e);
			try {
				errorDrawer.summarizeOn(this.canvas);
			} catch (Exception e1) {
				throw new RuntimeException(e1);
			}
		}
	}

	@Override
	protected void renderUsingPlugin(Tag tag, RenderingPlugin plugin)
			throws DoodleRenderException {
		plugin.renderSimplified(object, tag);
	}

	@Override
	protected void prepareTag(Tag tag, RenderingPlugin plugin) {
		tag.addCSSClass(plugin.getClassAttribute());
		tag.addCSSClass(plugin.getSmallCSSKeyword());
	}

}
