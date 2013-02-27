package game;

import ch.unibe.scg.doodle.api.DoodleCanvas;
import ch.unibe.scg.doodle.api.Doodleable;

public class Player implements Doodleable {
	
	private final String name;
	private boolean isAlive;
	private int lifePoints;
	
	public Player(String name) {
		this.name = name;
		this.lifePoints = 5;
		this.isAlive = true;
	}
	
	public void hit() {
		if (!isAlive)
			return;
		
		this.lifePoints--;
		if (lifePoints <= 0)
			this.isAlive = false;
	}
	
	public boolean isAlive() {
		return this.isAlive;
	}

	@Override
	public void doodleOn(DoodleCanvas c) {
		c.draw(name);
		c.newLine();
		c.draw("Alive?");
		c.draw(isAlive);
		c.newColumn();
		c.draw("Life points:");
		c.draw(lifePoints);
	}

	@Override
	public void summarizeOn(DoodleCanvas c) {
		c.draw(name);
		c.draw(isAlive);
	}

}
