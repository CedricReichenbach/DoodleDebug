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
		Field[] fields = this.object.getClass().getFields();
		System.out.println(this.object.getClass());
		for (Field f : fields) {
			try {
				s.draw(f.get(object));
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	public void drawSmallOn(Scratch s) {
		// TODO Auto-generated method stub

	}

}
