package plugins;

import html_generator.Tag;

import java.util.Set;

public abstract class AbstractPlugin implements RenderingPlugin {

	@Override
	public String getClassAttribute() {
		return this.getClass().getSimpleName();
	}

}
