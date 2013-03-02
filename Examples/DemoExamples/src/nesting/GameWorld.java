package nesting;

import java.util.ArrayList;
import java.util.List;

import ch.unibe.scg.doodle.api.DoodleCanvas;
import ch.unibe.scg.doodle.api.Doodleable;

public class GameWorld implements Doodleable {

	private String name;
	private List<Level> levels;

	public GameWorld(String name) {
		this.name = name;
		this.levels = new ArrayList<Level>();
	}

	public void addLevel(Level level) {
		this.levels.add(level);
	}

	@Override
	public void doodleOn(DoodleCanvas c) {
		c.draw("World: " + name);
		c.newLine();
		c.draw(levels);
	}

	@Override
	public void summarizeOn(DoodleCanvas c) {
		c.draw(name);
	}

}
