package ch.unibe.scg.doodle.util;

import java.util.List;

import javax.inject.Inject;

import ch.unibe.scg.doodle.ScratchFactory;
import ch.unibe.scg.doodle.helperClasses.NullObject;
import ch.unibe.scg.doodle.htmlgen.Tag;
import ch.unibe.scg.doodle.server.LightboxStack;
import ch.unibe.scg.doodle.server.util.DoodleImages;

public class BreadcrumbsBuilder {

	@Inject
	ScratchFactory scratchFactory;

	public void renderBreadcrumbs(LightboxStack stack, Tag tag) {
		Tag breadcrumbs = new Tag("div", "id=breadcrumbs");
		List<Object> objects = stack.bottomUpList();
		int depth = stack.depth() - 1;

		// in front of breadcrumbs
		Tag home = new Tag("div", "id=dd-home");
		String homeImgPath = DoodleImages.getDDIconPath();
		Tag homeImg = new Tag("img", "src=" + homeImgPath);
		homeImg.addAttribute("onclick", "javascript:hideLightbox()");
		home.add(homeImg);
		breadcrumbs.add(home);
		renderBetweenArrow(breadcrumbs);

		for (int i = 0; i < objects.size() - 1; i++) {
			Object o = objects.get(i);
			renderBreadcrumb(breadcrumbs, depth, o, "inactiveBreadcrumb");
			renderBetweenArrow(breadcrumbs);
			depth--;
		}
		if (!objects.isEmpty()) { // handle last one
			renderActiveBreadcrumb(breadcrumbs, objects.get(objects.size() - 1));
		}
		renderCutHeads(breadcrumbs, stack.cutHeadList());
		Tag breadcrumbsWrapper = new Tag("div", "id=breadcrumbsWrapper");
		breadcrumbsWrapper.add(breadcrumbs);
		tag.add(breadcrumbsWrapper);
	}

	private void renderBreadcrumb(Tag breadcrumbs, int depth, Object o,
			String cssClass) {
		Tag breadcrumb = new Tag("div", "class=breadcrumb");
		breadcrumb.addCSSClass(cssClass);
		breadcrumb.addAttribute("onclick", "javascript:breadcrumbsBack("
				+ depth + ")");
		String name = scratchFactory.create(o).getObjectTypeName();
		if (o instanceof NullObject)
			name = "?";
		breadcrumb.add(name);
		breadcrumbs.add(breadcrumb);
	}

	private void renderBetweenArrow(Tag breadcrumbs) {
		Tag between = new Tag("div", "class=betweenBreadcrumbs");
		between.add("&#x25B6;"); // Unicode: BLACK RIGHT-POINTING TRIANGLE
		breadcrumbs.add(between);
	}

	private void renderActiveBreadcrumb(Tag breadcrumbs, Object o) {
		renderBreadcrumb(breadcrumbs, 0, o, "activeBreadcrumb");
	}

	private void renderCutHeads(Tag breadcrumbs, List<Object> cutHeadList) {
		int depth = 0;
		Tag deadBreadcrumbs = new Tag("div", "class=deadBreadcrumbs");
		for (Object dead : cutHeadList) {
			renderBetweenArrow(deadBreadcrumbs);
			renderBreadcrumb(deadBreadcrumbs, --depth, dead, "deadBreadcrumb");
		}
		breadcrumbs.add(deadBreadcrumbs);
	}
}
