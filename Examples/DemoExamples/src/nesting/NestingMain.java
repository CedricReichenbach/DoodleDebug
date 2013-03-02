package nesting;

import game.Player;
import ch.unibe.scg.doodle.Doo;

public class NestingMain {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		GameWorld world = new GameWorld("Nirvana");
		Level level = new Level("Dark Dungeon", 80);
		Player player1 = new Player("Donny Jones");
		Player player2 = new Player("Marsha Mellow");
		Player player3 = new Player("Phil Wright");
		level.addPlayer(player1);
		level.addPlayer(player2);
		level.addPlayer(player3);
		Doo.dle(player1);
		world.addLevel(level);
		Doo.dle(new Object[] { world });
	}

}
