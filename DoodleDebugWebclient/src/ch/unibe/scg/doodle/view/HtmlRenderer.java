package ch.unibe.scg.doodle.view;

import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JScrollPane;

@Deprecated
public class HtmlRenderer {

	JFrame frame;
	private JEditorPane pane;
	private JScrollPane scrollPane;

	public HtmlRenderer() {
		frame = new JFrame("DoodleDebug");
		pane = new JEditorPane("text/html", "");
		scrollPane = new JScrollPane(pane);
		frame.getContentPane().add(scrollPane);
	}

	public void render(HtmlDocument htmlDocument) {
		this.render(htmlDocument.toString());
	}

	public void render(String html) {
		frame.getContentPane().remove(scrollPane);
		pane = new JEditorPane("text/html", html);
		pane.setEditable(false);
		scrollPane = new JScrollPane(pane);
		frame.getContentPane().add(scrollPane);

		initialize(frame);
	}

	private void initialize(JFrame frame) {
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setLocation(160, 100);
		frame.setSize(800, 500);
	}

}
