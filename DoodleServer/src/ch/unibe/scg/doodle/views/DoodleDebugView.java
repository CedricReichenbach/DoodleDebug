package ch.unibe.scg.doodle.views;

import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTError;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.ui.part.ViewPart;

public class DoodleDebugView extends ViewPart {

	public static final String ID = "ch.unibe.scg.doodle.views.DoodleDebugView";
	private static Browser browser;
	
	private static DoodleDebugView instance;

	@Override
	public void createPartControl(Composite parent) {
		try {
			browser = new Browser(parent, SWT.H_SCROLL | SWT.V_SCROLL);
		} catch (SWTError e) {
			MessageBox messageBox = new MessageBox(parent.getShell(),
					SWT.ICON_ERROR | SWT.OK);
			messageBox.setMessage("Browser cannot be initialized.");
			messageBox.setText("Exit");
			messageBox.open();
			System.exit(-1);
		}
		
		instance = this;
	}

	@Override
	public void setFocus() {
		browser.setText("Nothing to display yet...");
		browser.setFocus();
		System.out.println(browser);
	}
	
	public static void showHtml(String html) {
		if (instance == null) {
			System.out.println("Warning: Can't show HTML because DoodleDebug view was not created yet!");
			return;
		}
		
		instance.showHtmlDyn(html);
	}
	
	private void showHtmlDyn(String html) {
		browser.setText(html);
	}

}
