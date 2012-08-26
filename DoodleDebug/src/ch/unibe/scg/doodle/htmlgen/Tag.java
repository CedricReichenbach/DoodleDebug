package ch.unibe.scg.doodle.htmlgen;

import java.util.*;

public class Tag extends LinkedList {

	private Attributes attributes; // tag attributes
	private String name; // name of tag
	private boolean close; // closing tag y/n

	public Tag(String name) {
		super();
		close = true;
		this.name = name.toLowerCase();
		attributes = new Attributes();
	}

	public Tag(String name, boolean close) {
		this(name);
		this.close = close;
	}

	public Tag(String name, Attributes attr) {
		this(name);
		attributes = attr;
	}

	public Tag(String name, Attributes attr, boolean close) {
		this(name, attr);
		this.close = close;
	}

	public Tag(String name, String attr) {
		this(name);
		attributes = new Attributes(attr);
	}

	public Tag(String name, String attr, boolean close) {
		this(name, attr);
		this.close = close;
	}

	public Tag(String name, Attributes attr, List content) {
		super(content);
		this.name = name.toLowerCase();
		attributes = attr;
	}

	public Tag(String name, Attributes attr, List content, boolean close) {
		this(name, attr, content);
		this.close = close;
	}

	/**
	 * Create a new Tag with a specified name and several attributes
	 * 
	 * @param name
	 * @param attributes
	 */
	public Tag(String name, String... attributes) {
		this(name);
		this.attributes = new Attributes();
		for (String attr : attributes) {
			this.attributes.addAll(new Attributes(attr));
		}
	}

	/**
	 * Get the value of close.
	 * 
	 * @return Value of close.
	 */
	public boolean getClose() {
		return close;
	}

	/**
	 * Set the value of close.
	 * 
	 * @param v
	 *            Value to assign to close.
	 */

	public void setClose(boolean v) {
		this.close = v;
	}

	public String toString() {
		StringBuffer out = new StringBuffer("<" + name);
		out.append(attributes.toString());
		out.append(">");
		// content
		ListIterator iterator = super.listIterator();
		while (iterator.hasNext()) {
			out.append(iterator.next().toString());
		}
		if (close)
			out.append("</" + name + ">\n");
		return out.toString();
	}

	/**
	 * Get the value of attributes.
	 * 
	 * @return Value of attributes.
	 */
	public Attributes getAttributes() {
		return attributes;
	}

	/**
	 * Set the value of attributes.
	 * 
	 * @param v
	 *            Value to assign to attributes.
	 */
	public void setAttributes(Attributes v) {
		this.attributes = v;
	}

	public void addAttribute(Attribute attr) {
		attributes.add(attr);
	}

	public static String br() {
		return "<br>";
	}

	public static Object hr() {
		return "<hr>";
	}

	public void addAttribute(String name, String value) {
		this.addAttribute(new Attribute(name, value));
	}

	public void addCSSClass(String className) {
		Attribute classAttribute = attributes.getAttributeWithKey("class");
		if (classAttribute == null) {
			this.addAttribute("class", className);
		} else {
			classAttribute
					.setValue(classAttribute.getValue() + " " + className);
		}
	}

	public void addStyle(String style) {
		Attribute classAttribute = attributes.getAttributeWithKey("style");
		if (classAttribute == null) {
			this.addAttribute("style", style);
		} else {
			classAttribute.setValue(classAttribute.getValue() + ";" + style);
		}
	}

}
