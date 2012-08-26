package plugin_example;

import java.util.HashSet;
import java.util.Set;

import ch.unibe.scg.doodle.Doo;
import ch.unibe.scg.doodle.DoodleDebug;
import ch.unibe.scg.doodle.plugins.RenderingPlugin;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Phone phone = new Phone(41, "331234567");
		Fax fax = new Fax("034 456 78 90");
		Doo.dle(phone);
		Doo.dle(fax);

		Set<RenderingPlugin> set = new HashSet<RenderingPlugin>();
		set.add(new TelePlugin());
		DoodleDebug.addRenderingPlugins(set);

		Doo.dle(phone);
		Doo.dle(fax);
	}

}
