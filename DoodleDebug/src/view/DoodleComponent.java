package view;

import java.awt.Graphics;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.util.HashMap;

import javax.swing.JComponent;

public class DoodleComponent extends JComponent {

	private DoodleCanvas canvas;

	public DoodleComponent(DoodleCanvas canvas) {
		this.canvas = canvas;
	}

	private void evaluateCanvas(Graphics g) {
		for (Rect rect : canvas.getRects()) {
			this.drawRect(rect, g);
		}
		HashMap<DoodleCanvas, Rect> map = canvas.getCanvasMap();
		for (DoodleCanvas c : map.keySet()) {
			this.drawSub(c, map.get(c));
		}
	}

	private void drawSub(DoodleCanvas c, Rect rect) {
		double compWidth = this.getWidth();
		double compHeight = this.getHeight();

		double rectWidth = rect.getWidth() * compWidth;
		double rectHeight = rect.getHeight() * compHeight;

		Point2D.Double pos = this.scalePoint(rect.getPosition(), compWidth,
				compHeight);

		DoodleComponent subComponent = new DoodleComponent(c);
		subComponent.setBounds((int) pos.x, (int) pos.y, (int) rectWidth,
				(int) rectHeight);
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
