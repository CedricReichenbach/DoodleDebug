package ch.unibe.scg.doodle.server.views;

import java.util.Date;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTError;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;

import ch.unibe.scg.doodle.server.views.actions.TutorialButtonAction;
import ch.unibe.scg.doodle.util.PluginUtil;
import ch.unibe.scg.doodle.view.DoodleDebugWelcomeScreen;

public class DoodleDebugView extends ViewPart {

	public static final String ID = "ch.unibe.scg.doodle.views.DoodleDebugView";
	private Browser browser;

	private HtmlObserver htmlObserver = new HtmlObserver();

	private Date lastFocusSet;

	@Override
	public void createPartControl(Composite parent) {
		this.addTutorialButton();
		try {
			browser = new Browser(parent, SWT.NONE);
			browser.setJavascriptEnabled(true);
			DoodleLocationListener locationListener = new DoodleLocationListener();
			browser.addLocationListener(locationListener);
			String welcomeHtml = new DoodleDebugWelcomeScreen().toString();
			browser.setText(welcomeHtml);
			htmlObserver.htmlChangedTo(welcomeHtml);
		} catch (SWTError e) {
			MessageBox messageBox = new MessageBox(parent.getShell(),
					SWT.ICON_ERROR | SWT.OK);
			messageBox.setMessage("Browser cannot be initialized.");
			messageBox.setText("Exit");
			messageBox.open();
			System.exit(-1);
		}
	}

	private void addTutorialButton() {
		IActionBars actionBars = getViewSite().getActionBars();
		IToolBarManager toolBar = actionBars.getToolBarManager();

		Action action = new TutorialButtonAction();
		toolBar.add(action);

		actionBars.updateActionBars();
	}

	@Override
	public void setFocus() {
		browser.setFocus();
	}

	public void showHtml(String html) {
		browser.setText(html);
		htmlObserver.htmlChangedTo(html);

		this.activate();
	}

	public void runJavascript(String script) {
		boolean sucessful = browser.execute(script);
		if (!sucessful)
			System.err.println("WARNING: Could not execute a javascript");
		htmlObserver.htmlChangedTo(browser.getText());

		this.activate();
	}

	private void activate() {
		Date now = new Date();

		if (lastFocusSet == null
				|| (now.getTime() - lastFocusSet.getTime()) >= 4000) {
			PluginUtil.showDoodleDebugView();
		}
		lastFocusSet = now;
	}

	public void resetFocusTimer() {
		this.lastFocusSet = null;
	}

}
