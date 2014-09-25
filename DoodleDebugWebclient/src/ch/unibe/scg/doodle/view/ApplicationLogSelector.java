package ch.unibe.scg.doodle.view;

import java.util.Set;

import ch.unibe.scg.doodle.database.MetaInfo;
import ch.unibe.scg.doodle.htmlgen.Tag;
import ch.unibe.scg.doodle.jetty.WebsiteHandler;

public class ApplicationLogSelector extends DoodleDebugWelcomeScreen {

	public ApplicationLogSelector() {
		super();
		fillBody();
	}

	@SuppressWarnings("unchecked")
	private void fillBody() {
		Tag content = new Tag("div", "id=appLogSelector");
		Tag title = new Tag("h2");
		title.add("Select application log to read");
		content.add(title);
		Tag appList = new Tag("div");
		fillAppList(appList);
		content.add(appList);
		body.add(content);
	}

	@SuppressWarnings("unchecked")
	private void fillAppList(Tag appList) {
		Set<String> allApplicationNames = MetaInfo.getAllApplicationNames();
		for (String appName : allApplicationNames) {
			Tag link = new Tag("a");
			link.addAttribute("href", "?" + WebsiteHandler.APPLOG_GET_ARGNAME
					+ "=" + appName);
			link.add(appName);
			appList.add(link);
		}
		if (allApplicationNames.isEmpty()) {
			Tag div = new Tag("div", "class=no-logs-info");
			div.add("no application logs yet");
			appList.add(div);
		}
	}
}
