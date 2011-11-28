package view;

import javax.swing.JEditorPane;
import javax.swing.JFrame;

public class HtmlRenderer {

	public static void render(String html) {
		JFrame frame = new JFrame("DoodleDebug");
		
		JEditorPane pane = new JEditorPane("text/html", html);
		pane.setEditable(false);
		frame.getContentPane().add(pane);
		
		initialize(frame);
	}

	private static void initialize(JFrame frame) {
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setLocation(160, 100);
		frame.setSize(800, 500);
	}
	
}
