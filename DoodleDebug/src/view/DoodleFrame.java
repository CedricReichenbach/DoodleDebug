package view;

import javax.swing.JFrame;

public class DoodleFrame extends JFrame {

	public DoodleFrame(DoodleCanvas canvas) {
		super("first testframe");
		this.initialize();
		this.setContentPane(new DoodleComponent(canvas));
	}

	private void initialize() {
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setVisible(true);
	}

}
