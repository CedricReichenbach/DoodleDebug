package rendering;

import java.lang.reflect.Field;

import doodle.Drawable;
import doodle.Scratch;

/**
 * Has default drawOn methods for object that are not drawable.
 * @author Cedric Reichenbach
 *
 */
public class DrawableWrapper implements Drawable {
	
	Object object;

	public DrawableWrapper(Object o) {
		this.object = o;
	}
	
	@Override
	public void drawOn(Scratch s) {
		s.drawTitle(this.object.toString());
		Field[] fields = this.object.getClass().getFields();
		for (Field f : fields) {
			try {
				s.draw(f.get(object));
			} catch (IllegalArgumentException e) {
				throw new RuntimeException(e);
			} catch (IllegalAccessException e) {
				throw new RuntimeException(e);
			}
		}
	}

	@Override
	public void drawSmallOn(Scratch s) {
		// TODO Auto-generated method stub

	}

}
