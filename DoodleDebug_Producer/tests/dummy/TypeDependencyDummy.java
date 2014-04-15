package dummy;

import ch.unibe.scg.doodle.api.DoodleCanvas;
import ch.unibe.scg.doodle.api.Doodleable;

public class TypeDependencyDummy implements Doodleable, C {

	private A a;

	public TypeDependencyDummy() {
		this.a = new A(new B().toString());
	}

	@Override
	public void doodleOn(DoodleCanvas c) {
		this.summarizeOn(c);
		c.draw(a);
	}

	@Override
	public void summarizeOn(DoodleCanvas c) {
		c.draw("a TypeDependencyDummy");
	}

}
