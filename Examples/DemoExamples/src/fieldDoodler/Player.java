package fieldDoodler;


public class Player {
	
	protected final String name;
	protected boolean isAlive;
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

}
