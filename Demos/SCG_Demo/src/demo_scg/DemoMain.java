package demo_scg;

import java.io.IOException;
import java.util.Collection;
import java.util.LinkedList;

import ch.unibe.scg.doodle.Doo;
import ch.unibe.scg.doodle.DoodleDebug;
import ch.unibe.scg.doodle.plugins.RenderingPlugin;

import demo_scg.model.Directory;
import demo_scg.plugin.PhoneNumberPlugin;
import demo_scg.util.DemoUtil;

public class DemoMain {

	private static final int MAX_CONTACTS = 10;

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		Directory directory = new Directory();

		while (directory.numberOfContacts() < MAX_CONTACTS) {
			directory.addContact(DemoUtil.randomContact());
		}

		Doo.dle(directory);
//		System.out.println(directory);
		
		Doo.dle("asdf");
		
		Collection<RenderingPlugin> plugins = new LinkedList<>();
		plugins.add(new PhoneNumberPlugin());
		DoodleDebug.addRenderingPlugins(plugins);
	}
}
