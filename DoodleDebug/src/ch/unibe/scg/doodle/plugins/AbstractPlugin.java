package ch.unibe.scg.doodle.plugins;


import java.util.Set;

import ch.unibe.ch.scg.htmlgen.Tag;

public abstract class AbstractPlugin implements RenderingPlugin {

	@Override
	public String getClassAttribute() {
		return this.getClass().getSimpleName();
	}
	
	@Override
	public String getObjectTypeName(Object o) {
		return o.getClass().getSimpleName();
	}

}
