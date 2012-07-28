package ch.unibe.scg.doodle.rendering;

public class DoodleRenderException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String message;

	public DoodleRenderException(Exception e) {
		super(e);
	}

	public DoodleRenderException(String message) {
		this.message = message;
	}

	@Override
	public String getMessage() {
		return this.message + "\n" + super.getMessage();
	}

}
