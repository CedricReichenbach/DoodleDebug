package ch.unibe.scg.doodle;


import java.util.List;

import ch.unibe.scg.htmlgen.Tag;

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

	public abstract String getClassAttribute();

	void addCSSClass(String className);

	/**
	 * Defines nesting level of a Scratch/visualization
	 * @param i
	 */
	public abstract void setLevel(int i);

	public abstract void setObjectID(int store);

}