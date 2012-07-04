package ch.unibe.scg.doodle.rendering;


import javax.inject.Inject;
import javax.inject.Provider;

import ch.unibe.ch.scg.htmlgen.Tag;
import ch.unibe.scg.doodle.Doodler;


public class DefaultRendering implements Rendering<Object> {

	@Inject
	Doodler doodler;
	
	@Inject
	Provider<StringRendering> stringRenderingProvider;
	
	@Override
	public void render(Object object, Tag tag) {
		
		// simple testing example
		stringRenderingProvider.get().render(object.toString(), tag);
		
		// TODO
		//(new DrawableWrapper(object)).drawWhole(tag);
	}

}
