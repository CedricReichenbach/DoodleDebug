package visualization;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Iterator;

public class DefaultRenderings {
	final Graphics2D graphics;
	private Point2D upperLeft;
	private Shape clip;
	
	public DefaultRenderings(Graphics2D graphics) {
		this.graphics = graphics;
		this.upperLeft = new Point2D.Double(0, 0);
		this.clip = graphics.getClipBounds();
	}
	
	public void render(Object o ){
			if (o.getClass().isInstance(new ArrayList<Object>())) {
		
			this.drawArrayList((ArrayList<Object>) o);
		} else if (o.getClass().isInstance(Color.BLACK)) {
			this.drawColor((Color)o);
		} else {
			graphics.drawString(o.toString(), (int) (upperLeft.getX()),
					(int) (10 + upperLeft.getY()));
		}
	}
	
	private void drawColor(Color color) {
		// back up color
		Color lastColor = graphics.getColor();
		
		graphics.setColor(color);
		graphics.fill(this.clip);
		
		graphics.setColor(lastColor);
	}

	private void drawArrayList(ArrayList<Object> list) {
		int x = (int) (10 + this.upperLeft.getX());
		int y = (int) (10 + this.upperLeft.getY());
		int blockWidth = (int) (clip.getBounds2D().getWidth() - 20) / list.size();
		Iterator<Object> it = list.iterator();

		// back up clip
		Shape lastClip = this.clip;
		Point2D lastUL = this.upperLeft;

		while (it.hasNext()) {
			graphics.setClip(lastClip);
			upperLeft = lastUL;

			Object next = it.next();

			Rectangle box = new Rectangle(x, y, blockWidth, 30);
			graphics.draw(box);

			// if (graphics.getFontMetrics()
			// .getStringBounds(next.toString(), graphics).getWidth() <=
			// blockWidth - 4) {
			// graphics.drawString(next.toString(), x + 2, 10 + graphics
			// .getFontMetrics().getHeight());
			// } else {
			// graphics.drawString(this.getShortenedString(next.toString(),
			// blockWidth - 4), x + 2, 10 + graphics.getFontMetrics()
			// .getHeight());
			// }

			upperLeft = box.getLocation();
			clip = box;
			this.render(next);
			x += blockWidth;
		}
	}
}
