package doodle;

import html_generator.Tag;

import java.io.File;

public class HtmlImage {
	
	private final String mimeType;
	private final File file;

	public HtmlImage(File file, String mimeType) {
		this.mimeType = mimeType;
		this.file = file;
	}

	public Tag asHtml() {
		Tag img = new Tag("img", "src="+file.toURI().toString());
		return img;
	}
	
}
