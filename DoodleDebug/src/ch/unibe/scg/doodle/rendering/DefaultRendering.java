package ch.unibe.scg.doodle.rendering;


import java.awt.Color;
import java.awt.geom.Point2D;

import javax.inject.Inject;
import javax.inject.Provider;

import ch.unibe.ch.scg.htmlgen.Tag;
import ch.unibe.scg.doodle.Doodler;
import ch.unibe.scg.doodle.RealScratch;
import ch.unibe.scg.doodle.Scratch;
import ch.unibe.scg.doodle.ScratchFactory;
import ch.unibe.scg.doodle.view.DoodleCanvas;
import ch.unibe.scg.doodle.view.Rect;


public class DefaultRendering implements Rendering<Object> {

	@Inject
	Doodler doodler;
	
	@Inject
	Provider<StringRendering> stringRenderingProvider;
	
	@SuppressWarnings("unchecked")
	@Override
	public void render(Object object, Tag tag) {
		
		// simple testing example
		stringRenderingProvider.get().render(object.toString(), tag);
		
		// TODO
		//(new DrawableWrapper(object)).drawWhole(tag);
	}

}
