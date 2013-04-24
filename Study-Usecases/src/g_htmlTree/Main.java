package g_htmlTree;

import ch.unibe.scg.doodle.Doo;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Tag blog = WebsiteGenerator.generateBlog();
		System.out.println(blog);
	}

}
