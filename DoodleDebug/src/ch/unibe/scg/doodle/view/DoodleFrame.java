package ch.unibe.scg.doodle.view;

import javax.swing.JFrame;

/**
 * First test frame for visualization
 * @author Cedric Reichenbach
 *
 */
public class DoodleFrame extends JFrame {
	private static final long serialVersionUID = 1L;

	public DoodleFrame(DoodleCanvas canvas) {
		super("first testframe");
		this.initialize();
		this.setContentPane(new SwingAdapter(canvas));
	}

	private void initialize() {
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setVisible(true);
		this.setLocation(160, 100);
		this.setSize(800, 500);
	}

}
