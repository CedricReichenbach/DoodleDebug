package ch.unibe.scg.doodle.jetty;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.eclipse.jetty.server.handler.ResourceHandler;

import ch.unibe.scg.doodle.OutputManager;
import ch.unibe.scg.doodle.view.DoodleDebugWelcomeScreen;

public class WebsiteHandler extends AbstractHandler {

	private ResourceHandler resourceHandler;

	// FIXME: Really hardcode?
	private static final String RESOURCES_PATH = "resources";

	public WebsiteHandler() {
		resourceHandler = new ResourceHandler();
		resourceHandler.setDirectoriesListed(true);
		// resource_handler.setWelcomeFiles(new String[]{ "index.html" });
		resourceHandler.setResourceBase(RESOURCES_PATH);
	}

	@Override
	public void handle(String target, Request baseRequest,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		// XXX: Is this ok?
		if (isResource(target)) {
			resourceHandler.handle(target, baseRequest, request, response);
			return;
		}

		response.setContentType("text/html;charset=utf-8");
		response.setStatus(HttpServletResponse.SC_OK);
		baseRequest.setHandled(true);
		String welcome = new DoodleDebugWelcomeScreen().toString();
		// String baseHtml = OutputManager.instance().initOutput();
		response.getWriter().println(welcome);
	}

	private boolean isResource(String target) {
		// TODO: Cleaner way?
		return target.startsWith("/js/") | target.startsWith("/css/")
				| target.startsWith("/img/") | target.startsWith("/tutorials/");
	}

}
