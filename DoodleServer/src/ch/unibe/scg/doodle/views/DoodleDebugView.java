package ch.unibe.scg.doodle.views;

import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTError;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.ui.part.ViewPart;

public class DoodleDebugView extends ViewPart {

	public static final String ID = "ch.unibe.scg.doodle.views.DoodleDebugView";
	private Browser browser;

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
	}

	@Override
	public void setFocus() {
		browser.setText("Nothing to display yet...");
		browser.setFocus();
	}

	public void showHtml(String html) {
		browser.setText(html);
	}

}
