package ch.unibe.scg.doodle.plugins;

public abstract class AbstractPlugin implements RenderingPlugin {

	/**
	 * {@inheritDoc}
	 * Default is java class name of the implementing plugin class (e.g. "ImagePlugin").
	 */
	@Override
	public String getClassAttribute() {
		return this.getClass().getSimpleName();
	}

	/**
	 * {@inheritDoc}
	 * Standard: "smallRendering"
	 */
	@Override
	public String getSmallCSSKeyword() {
		return "smallRendering";
	}

	@Override
	public String getObjectTypeName(Object o) {
		return o.getClass().getSimpleName();
	}

	@Override
	public String getCSS() {
		return "";
	}
}
