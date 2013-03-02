package bool;

import ch.unibe.scg.doodle.Doo;

public class BoolMain {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Boolean[][] tttState1 = {{true, false, false},{null, true, null},{null, null, false}};
		TicTacToe ttt1 = new TicTacToe(tttState1);
		
		Doo.dle(ttt1);
	}

}
