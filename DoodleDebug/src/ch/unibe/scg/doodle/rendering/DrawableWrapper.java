package ch.unibe.scg.doodle.rendering;

import java.lang.reflect.Field;

import ch.unibe.scg.doodle.Doodleable;
import ch.unibe.scg.doodle.Scratch;


/**
 * Has default drawOn methods for object that are not drawable.
 * @author Cedric Reichenbach
 *
 */
public class DrawableWrapper implements Doodleable {
	
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
