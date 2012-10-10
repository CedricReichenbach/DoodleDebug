package g_htmlTree;

import java.util.Random;

public class WebsiteGenerator {

	private static final int NUM_PARAGRAPHS = 5;
	private static final int NUM_WORDS = 20;

	private static final String[] WORDS = { "ipsum", "dolor", "ermahgerd",
			"flubar" };

	public static Tag generateBlog() {
		Tag html = new Tag("html");
		Tag body = new Tag("body");
		Tag h1 = new Tag("h1");
		h1.add("My Interesting Blog");
		body.add(h1);
		for (int i = 0; i < NUM_PARAGRAPHS; i++) {
			Tag h2 = new Tag("h2");
			h2.add("Story " + (i + 1));
			body.add(h2);
			Tag p = new Tag("p");
			createRandomText(p);
			body.add(p);
		}
		html.add(body);
		return html;
	}

	private static void createRandomText(Tag p) {
		Tag wrapper = new Tag("div", "class=paragraphWrapper");
		for (int i = 0; i < NUM_WORDS; i++) {
			wrapper.add(randomWord() + " ");
		}
		wrapper.add(".");
		p.add(wrapper);

	}

	private static String randomWord() {
		int max = WORDS.length;
		return WORDS[(int) (Math.random() * max)];
	}
}
