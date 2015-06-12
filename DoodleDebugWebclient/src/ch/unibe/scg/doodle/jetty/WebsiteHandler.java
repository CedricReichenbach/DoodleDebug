package ch.unibe.scg.doodle.jetty;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.eclipse.jetty.server.handler.ResourceHandler;

import resources.ResourceBase;
import ch.unibe.scg.doodle.OutputManager;
import ch.unibe.scg.doodle.util.ApplicationUtil;
import ch.unibe.scg.doodle.view.ApplicationLogSelector;

public class WebsiteHandler extends AbstractHandler {

	public static final String APPLOG_GET_ARGNAME = "applog";

	private ResourceHandler resourceHandler;
	// private WebSocketHandler webSocketHandler;

	public WebsiteHandler() {
		resourceHandler = new ResourceHandler();
		resourceHandler.setDirectoriesListed(true);
		resourceHandler.setResourceBase(ResourceBase.getLocation());
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

		String applog = request.getParameter(APPLOG_GET_ARGNAME);
		if (applog != null) {
			// TODO: Make this session specific
			ApplicationUtil.setApplicationName(applog);
			String applogHtml = OutputManager.instance().initOutput();
			response.getWriter().println(applogHtml);
			return;
		}

		String welcome = new ApplicationLogSelector().toString();
		response.getWriter().println(welcome);
	}

	private boolean isResource(String target) {
		// TODO: Cleaner way?
		String[] resourcePaths = { "js/", "css/", "img/", "tutorials/" };
		for (String resourcePath : resourcePaths)
			if (target.startsWith(resourcePath)
					| target.startsWith("/" + resourcePath))
				return true;
		return false;
	}

}
