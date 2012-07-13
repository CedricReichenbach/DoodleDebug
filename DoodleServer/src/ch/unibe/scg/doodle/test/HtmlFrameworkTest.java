package ch.unibe.scg.doodle.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import ch.unibe.scg.doodle.htmlgen.Tag;

public class HtmlFrameworkTest {

	private Tag div;
	private Tag div2;

	@Before
	public void init() {
		div = new Tag("div");
		div.add("text");
		div2 = new Tag("div");
	}

	@Test
	public void testAttributeChanges() {
		System.out.println(div.toString());
		assertEquals(div.toString(), "<div>text</div>\n");
		div.addAttribute("class", "test");
		assertEquals(div.toString(), "<div class=\"test\">text</div>\n");
		div.addCSSClass("another");
		assertEquals(div.toString(), "<div class=\"test another\">text</div>\n");
		div2.addCSSClass("some");
		assertEquals(div2.toString(), "<div class=\"some\"></div>\n");
	}
}
