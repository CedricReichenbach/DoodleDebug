package ch.unibe.scg.doodle.view;

import ch.unibe.scg.doodle.hbase.MetaInfo;
import ch.unibe.scg.doodle.htmlgen.Tag;
import ch.unibe.scg.doodle.server.views.DoodleLocationCodes;

// TODO: Integrate into eclipse view (to be able to change anytime) and update periodically
public class ApplicationLogSelector extends HtmlDocument {

	public ApplicationLogSelector() {
		super();
		this.body = createBody();
	}

	@SuppressWarnings("unchecked")
	private Tag createBody() {
		Tag body = new Tag("body");
		Tag content = new Tag("div");
		Tag title = new Tag("h1");
		title.add("Select application log to read");
		content.add(title);
		Tag appList = new Tag("div");
		fillAppList(appList);
		content.add(appList);
		body.add(content);
		return body;
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
