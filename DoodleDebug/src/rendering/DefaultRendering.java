package rendering;

import html_generator.Tag;

import java.awt.Color;
import java.awt.geom.Point2D;

import javax.inject.Inject;

import doodle.Doodler;
import doodle.RealScratch;
import doodle.ScratchFactory;
import view.DoodleCanvas;
import view.Rect;

public class DefaultRendering implements Rendering<Object> {

	@Inject
	Doodler doodler;
	
	@Override
	public void render(Object object, Tag tag) {
		// TODO
		//(new DrawableWrapper(object)).drawWhole(tag);
	}

}
