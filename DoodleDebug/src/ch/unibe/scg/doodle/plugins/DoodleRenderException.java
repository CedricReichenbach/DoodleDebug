package ch.unibe.scg.doodle.plugins;

/**
 * Exception representing any problems when rendering an object.
 * @author Cedric Reichenbach
 *
 */
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
