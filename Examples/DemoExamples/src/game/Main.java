package game;

import java.util.List;
import java.util.Random;

import ch.unibe.scg.doodle.Doo;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		GameRoom room = new GameRoom("Narnia");
		while (room.numberOfPlayers() < 6) {
			room.addPlayer(new Player(randomName()));
		}
		
		while (!room.hasWinner()) {
			List<Player> list = room.playerList();
			list.get(random(list.size())).hit();
			Doo.dle(room);
		}
	}

	static String[] names = {"Leo", "Jack", "Jenny", "Tommy", "Elizabeth", "Ronald", "Amadeus", "Zack"};
	private static String randomName() {
		return names[random(names.length)];
	}
	private static int random(int limit) {
		return new Random().nextInt(limit);
	}

}
