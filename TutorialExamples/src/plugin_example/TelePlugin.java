package plugin_example;

import java.util.HashSet;
import java.util.Set;

import ch.unibe.scg.doodle.htmlgen.Tag;
import ch.unibe.scg.doodle.plugins.AbstractPlugin;
import ch.unibe.scg.doodle.rendering.DoodleRenderException;

public class TelePlugin extends AbstractPlugin {

	private PhoneRendering phoneRendering;
	private FaxRendering faxRendering;

	public TelePlugin() {
		this.phoneRendering = new PhoneRendering();
		this.faxRendering = new FaxRendering();
	}

	@Override
	public Set<Class<?>> getDrawableClasses() {
		Set<Class<?>> set = new HashSet<Class<?>>();
		set.add(Phone.class);
		set.add(Fax.class);
		return set;
	}

	@Override
	public void render(Object object, Tag tag) throws DoodleRenderException {
		if (object instanceof Phone) {
			phoneRendering.render((Phone) object, tag);
		} else if (object instanceof Fax) {
			faxRendering.render((Fax) object, tag);
		}
	}

	@Override
	public void renderSimplified(Object object, Tag tag)
			throws DoodleRenderException {
		if (object instanceof Phone) {
			new PhoneRendering().renderSimplified((Phone) object, tag);
		} else if (object instanceof Fax) {
			new FaxRendering().renderSimplified((Fax) object, tag);
		}
	}

	// Optional. Standard implementation returns an empty string (= no custom
	// css).
	@Override
	public String getCSS() {
		return ".TelePlugin .fax-number {background-color: #fdb; vertical-align: middle;} "
				+ ".TelePlugin .phone-number {background-color: #cfd}";
	}
}
