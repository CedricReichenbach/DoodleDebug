package game;

import java.util.ArrayList;
import java.util.List;

import ch.unibe.scg.doodle.api.DoodleCanvas;
import ch.unibe.scg.doodle.api.Doodleable;

public class GameRoom implements Doodleable {
	private List<Player> players;
	private String roomName;

	public GameRoom(String roomName) {
		this.roomName = roomName;
		this.players = new ArrayList<Player>();
	}

	public void addPlayer(Player player) {
		this.players.add(player);
	}
	
	public List<Player> playerList() {
		return players;
	}

	public int numberOfPlayers() {
		return this.players.size();
	}

	public boolean hasWinner() {
		int aliveCount = 0;
		for (Player p : players)
			if (p.isAlive())
				aliveCount++;
		return aliveCount <= 1;
	}

	@Override
	public void doodleOn(DoodleCanvas c) {
		c.draw("Room: "+roomName);
		c.draw(players);
	}

	@Override
	public void summarizeOn(DoodleCanvas c) {
		c.draw(roomName);
	}
}
