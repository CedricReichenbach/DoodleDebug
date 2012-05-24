package ch.unibe.scg.doodle.helperClasses;

import java.lang.reflect.Field;

import ch.unibe.scg.doodle.api.DoodleCanvas;
import ch.unibe.scg.doodle.api.Doodleable;

/**
 * Has default drawOn methods for object that are not drawable.
 * 
 * @author Cedric Reichenbach
 * 
 */
public class DrawableWrapper implements Doodleable {

	Object object;

	public DrawableWrapper(Object o) {
		this.object = o;
	}

	@Override
	public void drawOn(DoodleCanvas c) {
		Field[] fields = this.object.getClass().getFields();
		for (Field f : fields) {
			try {
				c.draw(f.get(object));
			} catch (IllegalArgumentException e) {
				throw new RuntimeException(e);
			} catch (IllegalAccessException e) {
				throw new RuntimeException(e);
			}
		}
	}

	@Override
	public void drawSmallOn(DoodleCanvas c) {
		// TODO Auto-generated method stub

	}

}
