package visualization;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Point2D;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Doodler {

	private BasicWindow window;
	private Graphics2D graphics;
	private DefaultRenderings defaultRenderings;

	public Doodler(BasicWindow window) {
		this.window = window;
		this.graphics = window.getGraphics();
		this.defaultRenderings = new DefaultRenderings(graphics);

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
//		graphics.setClip(clip); TODO

		try {
			Class[] da = new Class[]{this.getClass()};
			Method m = o.getClass().getMethod("draw", da);
			if(o instanceof Drawable)
				((Drawable) o).draw(this);
			try {
				m.invoke(o, this);
			} catch (IllegalArgumentException e) {
				throw new RuntimeException(e);
			} catch (IllegalAccessException e) {
				throw new RuntimeException(e);
			} catch (InvocationTargetException e) {
				throw new RuntimeException(e);
			}
		} catch (NoSuchMethodException nsme) {
			defaultRenderings.render(o);
			
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