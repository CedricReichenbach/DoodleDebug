package ch.unibe.scg.doodle.rendering;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import ch.unibe.scg.doodle.htmlgen.Tag;

public class StringRendering implements Rendering<String> {

	private final int maxLength = 50;

	@SuppressWarnings("unchecked")
	@Override
	public void render(String string, Tag tag) {
		Tag p = new Tag("p", "class=StringRendering");
		
		p.add(replaceEscapeSeq(string));
		tag.add(p);
	}

	@Override
	public void renderSimplified(String string, Tag tag) {
		Tag p = new Tag("p", "class=StringRendering");

		if (string.length() > maxLength) {
			// split between words
			List<String> words = Arrays.asList(string.split(" "));
			Iterator<String> it = words.iterator();
			String result = "";
			while (it.hasNext()) {
				String next = it.next();
				if ((result + next).length() > maxLength) {
					int i = result.lastIndexOf(" ");
					result = result.substring(0, i);
					break;
				} else
					result += next + " ";
			}
			if (it.hasNext()) {
				string = result + "...";
			} else {
				string = result;
			}
			// string = string.substring(0, maxLength - 1) + "...";
		}

		p.add(replaceEscapeSeq(string));
		tag.add(p);
	}

	private String replaceEscapeSeq(String string) {
		string = string.replace("<", "&lt;").replace(">", "&gt;");
		string = string.replace("\"", "&quot").replace("'", "&#39;");
		string = string.replace("/", "&x2F;");
		return string.replace("\n", "<br>");
	}

}
