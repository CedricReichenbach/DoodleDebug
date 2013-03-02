package nesting;

import game.Player;

import java.util.ArrayList;
import java.util.List;

import ch.unibe.scg.doodle.api.DoodleCanvas;
import ch.unibe.scg.doodle.api.Doodleable;

public class Level implements Doodleable {

	private String name;
	private int difficulty;
	private List<Player> players;

	public Level(String name, int difficulty) {
		this.name = name;
		this.difficulty = difficulty;
		this.players = new ArrayList<Player>();
	}

	public void addPlayer(Player player) {
		this.players.add(player);
	}

	@Override
	public void doodleOn(DoodleCanvas c) {
		c.draw(name);
		c.newLine();
		c.draw("Difficulty: " + difficulty);
		c.newLine();
		c.draw(players);
	}

	@Override
	public void summarizeOn(DoodleCanvas c) {
		c.draw(name);
	}

}
