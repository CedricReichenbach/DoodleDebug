package rendering;

import html_generator.Tag;

import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;

import view.DoodleCanvas;
import view.Rect;
import doodle.Scratch;

public class ScratchRendering implements Rendering<Scratch> {

	@Override
	public void render(Scratch scratch, Tag tag) {
		// TODO

//		// frame
//		Rect mainRect = new Rect(new Point2D.Double(0.01, 0.01), .8, .98);
//		canvas.drawRect(mainRect);
//
//		// title
//		canvas.drawText(scratch.getTitle(), new Point2D.Double(.02, .02));
//
//		// inner
//		double dHeight = .01;
//		double subWidth = .7;
//		double subHeight = .88 / scratch.getInner().size() - dHeight;
//		Point2D.Double curPoint = new Point2D.Double(.06, .06);
//		for (Scratch s : scratch.getInner()) {
//			Rect rect = new Rect(curPoint, subWidth, subHeight);
//			DoodleCanvas c = new DoodleCanvas();
//			s.drawWhole(c);
//			canvas.drawCanvas(c, rect);
//			curPoint = new Point2D.Double(curPoint.x, curPoint.y + subHeight
//					+ dHeight);
//		}
//
//		// outer

	}
}
