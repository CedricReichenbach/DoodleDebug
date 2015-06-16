package ch.unibe.scg.doodle.jetty;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.util.resource.Resource;

public class JarResourceHandler extends AbstractHandler {

	// XXX: Standardize globally somehow for DD?
	private static final File TEMP_DIR = new File(
			System.getProperty("java.io.tmpdir")
					+ "/DoodleDebug/webapp-resources");

	private final ResourceHandler resourceHandler;

	public JarResourceHandler(String resourceBase) {
		TEMP_DIR.mkdirs();

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

		File jarFile = new File(getClass().getProtectionDomain()
				.getCodeSource().getLocation().getPath());
		JarFile jar = new JarFile(jarFile);
		Enumeration<JarEntry> entries = jar.entries();
		while (entries.hasMoreElements()) {
			JarEntry entry = entries.nextElement();
			final String name = entry.getName();
			if (name.endsWith(target)) {
				ServletOutputStream outputStream = response.getOutputStream();
				response.setContentType(guessContentType(name));
				copy(jar.getInputStream(entry), outputStream);
			}
		}
		jar.close();

		baseRequest.setHandled(true);
	}

	private void copy(InputStream inputStream, OutputStream outputStream)
			throws IOException {
		byte[] buffer = new byte[1024];
		int len;
		while ((len = inputStream.read(buffer)) != -1) {
			outputStream.write(buffer, 0, len);
		}
	}

	/**
	 * Guess content type from file name.
	 * 
	 * @param name
	 * @return
	 */
	private String guessContentType(String name) {
		String[] parts = name.split("\\.");
		String suffix = parts[parts.length - 1];
		if ("js".equals(suffix))
			suffix = "javascript";
		return "text/" + suffix;
	}

}
