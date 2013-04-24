package demo_scg;

import java.io.IOException;
import java.util.Collection;
import java.util.LinkedList;

import ch.unibe.scg.doodle.DoodleDebug;
import ch.unibe.scg.doodle.plugins.RenderingPlugin;
import demo_scg.model.Directory;
import demo_scg.plugin.StringPlugin;
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

		System.out.println(directory);
	}

	@SuppressWarnings("unused")
	private static void addStringPlugin() {
		Collection<RenderingPlugin> plugins = new LinkedList<>();
		plugins.add(new StringPlugin());
		DoodleDebug.addRenderingPlugins(plugins);
	}
}
