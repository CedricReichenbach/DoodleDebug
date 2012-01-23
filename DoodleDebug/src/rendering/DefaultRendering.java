package rendering;

import html_generator.Tag;

import java.awt.Color;
import java.awt.geom.Point2D;

import javax.inject.Inject;
import javax.inject.Provider;

import doodle.Doodler;
import doodle.RealScratch;
import doodle.Scratch;
import doodle.ScratchFactory;
import view.DoodleCanvas;
import view.Rect;

public class DefaultRendering implements Rendering<Object> {

	@Inject
	Doodler doodler;
	
	@Inject
	ScratchFactory scratchFactory;
	
	@Override
	public void render(Object object, Tag tag) {
		
		// simple testing example
		scratchFactory.create(object.toString()).drawWhole(tag);
		
		// TODO
		//(new DrawableWrapper(object)).drawWhole(tag);
	}

}
