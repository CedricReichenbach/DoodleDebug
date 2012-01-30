package doodle;

public class ErrorDrawer implements Doodleable {
	
	private final Exception exception;
	
	public ErrorDrawer(Exception e) {
		this.exception = e;
	}

	@Override
	public void drawOn(Scratch s) throws Exception {
		s.draw(this.exception.getStackTrace());
		exception.printStackTrace();
	}

	@Override
	public void drawSmallOn(Scratch s) throws Exception {
		s.draw(this.exception);
	}

}
