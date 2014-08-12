package ch.unibe.scg.doodle.view;

import ch.unibe.scg.doodle.database.MetaInfo;
import ch.unibe.scg.doodle.htmlgen.Tag;
import ch.unibe.scg.doodle.server.views.DoodleLocationCodes;

// TODO: Integrate into eclipse view (to be able to change anytime) and update periodically
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
		body.push(content);
	}

	@SuppressWarnings("unchecked")
	private void fillAppList(Tag appList) {
		for (String appName : MetaInfo.getAllApplicationNames()) {
			Tag link = new Tag("a");
			link.addAttribute("href", DoodleLocationCodes.APP_LOG_PREFIX
					+ appName);
			link.add(appName);
			appList.add(link);
		}
	}
}
