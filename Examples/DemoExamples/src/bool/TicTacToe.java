package bool;

import ch.unibe.scg.doodle.api.DoodleCanvas;
import ch.unibe.scg.doodle.api.Doodleable;

public class TicTacToe implements Doodleable {
	
	private Boolean[][] state;

	public TicTacToe(Boolean[][] state) {
		this.state = state;
	}

	@Override
	public void doodleOn(DoodleCanvas c) {
		c.draw(state);		
	}

	@Override
	public void summarizeOn(DoodleCanvas c) {
		c.draw(state);
	}

}
