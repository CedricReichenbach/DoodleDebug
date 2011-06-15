package visualization;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class Drawer {

	private BasicWindow window;
	private Graphics2D graphics;
	private Shape clip;
	private Point2D upperLeft;

	public Drawer(BasicWindow window) {
		this.window = window;
		this.graphics = window.getGraphics();
		this.clip = window.getBounds();
		this.upperLeft = new Point2D.Double(0, 0);

		graphics.setColor(Color.WHITE);
	}

	public void draw(Object o) {
		this.drawSub(o);
		window.renderBuffer();
	}

	private void drawSub(Object o) {
		// graphics may have changed (e.g. by resize)
		this.graphics = window.getGraphics();
		graphics.setColor(Color.WHITE);
		graphics.setClip(clip);

		try {
			Class[] da = new Class[]{this.getClass()};
			o.getClass().getMethod("draw", da);
			try {
			((Drawable) o).draw(this);
			} catch (ClassCastException e) {
				e.printStackTrace();
				throw new NoSuchMethodException();
			}
		} catch (NoSuchMethodException nsme) {
			if (o.getClass().isInstance(new ArrayList<Object>())) {
				this.drawArrayList((ArrayList<Object>) o);
			} else if (o.getClass().isInstance(Color.BLACK)) {
				this.drawColor((Color)o);
			} else {
				graphics.drawString(o.toString(), (int) (upperLeft.getX()),
						(int) (10 + upperLeft.getY()));
			}
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
		int blockWidth = (int) (window.getWidth() - 20) / list.size();
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
			this.drawSub(next);
			x += blockWidth;
		}
	}

	private String getShortenedString(String string, int size) {
		FontMetrics metrics = graphics.getFontMetrics();

		double dotsWidth = metrics.getStringBounds("...", graphics).getWidth();

		String result = "";
		int index = 0;
		double resWidth = 0;
		while (resWidth + dotsWidth < size) {
			result += string.charAt(index);
			resWidth = metrics.getStringBounds(result, graphics).getWidth()
					+ metrics.getStringBounds(string.charAt(index) + "",
							graphics).getWidth(); // counting next character too
			index++;
		}
		result += "...";
		return result;
	}

}