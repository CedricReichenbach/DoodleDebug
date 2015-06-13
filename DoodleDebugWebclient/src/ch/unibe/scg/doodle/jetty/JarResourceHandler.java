package ch.unibe.scg.doodle.jetty;

import java.io.File;
import java.io.IOException;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.util.resource.Resource;

public class JarResourceHandler extends AbstractHandler {

	private final ResourceHandler resourceHandler;

	public JarResourceHandler(String resourceBase) {
		resourceHandler = new ResourceHandler();
		resourceHandler.setDirectoriesListed(true);
		resourceHandler.setResourceBase(resourceBase);
	}

	@Override
	public void handle(String target, Request baseRequest,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		Resource resource = resourceHandler.getResource(target);
		if (resource != null && resource.exists()) {
			resourceHandler.handle(target, baseRequest, request, response);
			return;
		}
		
		// TODO: Extract file to temp dir and serve
//		File jarFile = new File(getClass().getProtectionDomain().getCodeSource().getLocation().getPath());
//		JarFile jar = new JarFile(new File(getClass().getProtectionDomain().getCodeSource().getLocation().getPath()));
//		Enumeration<JarEntry> entries = jar.entries();
//		while(entries.hasMoreElements()) {
//			java.util.jar.JarEntry entry = entries.nextElement();
//		    final String name = entry.getName();
//		    if (name.endsWith(target)) {
//		        // XXX
//		    }
//		}
	}

}
