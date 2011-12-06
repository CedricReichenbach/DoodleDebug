package doodle;

import html_generator.Tag;

import java.util.List;

public interface Scratch {

	/**
	 * Visualize any Object on this whole Scratch (not inside another object).
	 * 
	 * 
	 * @param canvas 
	 */
	public abstract void drawWhole(Tag tag);

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
	 * Visualizes any Object outside of the caller, either using draw Method of
	 * the object itself (if existing) or does a default drawing. Should only be
	 * called from another draw Method.
	 * 
	 * @param Object
	 *            o
	 */
	public abstract void drawOuter(Object o);

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

	public abstract List<RealScratch> getInner();

	public abstract List<RealScratch> getOuter();

	public abstract String getTitle();

	public abstract String getClassAttribute();

}