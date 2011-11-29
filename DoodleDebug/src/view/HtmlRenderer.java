package view;

import javax.swing.JEditorPane;
import javax.swing.JFrame;

public class HtmlRenderer {

	JFrame frame;
	private JEditorPane pane;

	public HtmlRenderer() {
		frame = new JFrame("DoodleDebug");
		pane = new JEditorPane("text/html", "");
		frame.getContentPane().add(pane);
	}

	public void render(String html) {
		frame.getContentPane().remove(pane);
		pane = new JEditorPane("text/html", html);
		pane.setEditable(false);
		frame.getContentPane().add(pane);

		initialize(frame);
	}

	private void initialize(JFrame frame) {
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setLocation(160, 100);
		frame.setSize(800, 500);
	}

}
