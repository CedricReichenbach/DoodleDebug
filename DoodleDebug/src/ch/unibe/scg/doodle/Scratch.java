package ch.unibe.scg.doodle;


import java.io.IOException;
import java.util.List;

import ch.unibe.ch.scg.htmlgen.Attribute;
import ch.unibe.ch.scg.htmlgen.Tag;

public interface Scratch {

	/**
	 * Visualize any Object on this whole Scratch (not inside another object).
	 * 
	 * 
	 * @param canvas 
	 * @throws Exception 
	 */
	public abstract void drawWhole(Tag tag) ;

	public abstract void drawWholeWithName(Tag div);

	/**
	 * Visualizes any Object inside the caller, either using draw Method of the
	 * object itself (if existing) or does a default drawing. Should only be
	 * called from another draw Method.
	 * 
	 * @param Object
	 *            o
	 */
	public abstract void draw(Object o);

	/**
	 * Visualizes any Object with few details, either using drawSmall Method of
	 * the object itself (if existing) or does a default drawing.
	 * 
	 * @param Object
	 *            o
	 */
	public abstract void drawSmall(Object o);

	/**
	 * Sets a title for object to be visualized
	 * 
	 * @param String
	 *            title
	 */
	public abstract void drawTitle(String title);

	public abstract List<List<List<Scratch>>> getColumns();

	public abstract String getTitle();

	public abstract String getClassAttribute();

	public abstract void newLine();

	public abstract void newColumn();
	
	public abstract void drawImage(byte[] image, String mimeType) throws IOException;

	public abstract void addAttribute(Attribute attribute);

}