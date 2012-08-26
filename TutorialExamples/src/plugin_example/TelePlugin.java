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
	public void renderSmall(Object object, Tag tag)
			throws DoodleRenderException {
		if (object instanceof Phone) {
			new PhoneRendering().renderSmall((Phone) object, tag);
		} else if (object instanceof Fax) {
			new FaxRendering().renderSmall((Fax) object, tag);
		}
	}

	// Optional. Standard implementation returns an empty string (= no css).
	@Override
	public String getCSS() {
		return ".TelePlugin .fax-number {background-color: #fdb} "
				+ ".TelePlugin .phone-number {background-color: #cfd}";
	}

}
