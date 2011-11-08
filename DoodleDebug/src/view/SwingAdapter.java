package view;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.util.HashMap;

import javax.swing.JComponent;
import javax.swing.JLabel;

/**
 * Test component for painting DoodleCanvas
 * 
 * @author Cedric Reichenbach
 * 
 */
public class SwingAdapter extends JComponent {

	private DoodleCanvas canvas;

	public SwingAdapter(DoodleCanvas canvas) {
		this.canvas = canvas;
	}

	private void evaluateCanvas(Graphics g) {
		// delete everything
		this.removeAll();
		
		// draw rects
		for (Rect rect : canvas.getRects()) {
			this.drawRect(rect, g);
		}

		// draw sub-canvas'
		HashMap<DoodleCanvas, Rect> canMap = canvas.getCanvasMap();
		for (DoodleCanvas c : canMap.keySet()) {
			this.drawSub(c, canMap.get(c));
		}

		// draw texts
		HashMap<String, Point2D.Double> textMap = canvas.getTextMap();
		for (String s : textMap.keySet()) {
			this.drawText(s, textMap.get(s), g);
		}
	}

	private void drawText(String string, Point2D.Double position, Graphics g) {
		position = this.scalePoint(position, this.getWidth(), this.getHeight());
		g.drawString(string, (int) position.x, (int) position.y + 8);
	}

	private void drawSub(DoodleCanvas c, Rect rect) {
		double compWidth = this.getWidth();
		double compHeight = this.getHeight();

		double rectWidth = rect.getWidth() * compWidth;
		double rectHeight = rect.getHeight() * compHeight;

		Point2D.Double pos = this.scalePoint(rect.getPosition(), compWidth,
				compHeight);

		SwingAdapter subComponent = new SwingAdapter(c);
		subComponent.setBounds((int) pos.x, (int) pos.y, (int) rectWidth,
				(int) rectHeight);
		
		// TODO: not working that way...
		this.add(subComponent);
	}

	private void drawRect(Rect rect, Graphics g) {
		double compWidth = this.getWidth();
		double compHeight = this.getHeight();

		double rectWidth = rect.getWidth() * compWidth;
		double rectHeight = rect.getHeight() * compHeight;

		Point2D.Double pos = this.scalePoint(rect.getPosition(), compWidth,
				compHeight);

		g.drawRect((int) pos.x, (int) pos.y, (int) rectWidth, (int) rectHeight);
		// TODO: more details
	}

	private Point2D.Double scalePoint(Point2D.Double point, double x, double y) {
		return new Point2D.Double(point.getX() * x, point.getY() * y);
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		this.evaluateCanvas(g);
	}

}
