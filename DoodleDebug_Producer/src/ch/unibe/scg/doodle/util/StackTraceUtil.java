package ch.unibe.scg.doodle.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class StackTraceUtil {
	public static String linkClasses(String string) {
		List<String> parts1 = Arrays.asList(string.split("\\)"));
		List<String> innerResults = new LinkedList<String>();
		for (String halfSplitted : parts1) {
			List<String> parts = Arrays.asList(halfSplitted.split("\\("));
			List<String> prepared = new LinkedList<String>();
			String lastpart = "";
			for (String part : parts) {
				if (isClass(part)) {
					try {
						String[] stuff = lastpart.split("at ");
						String fullMethodName = stuff[stuff.length - 1];
						String classAndLine = fullClassWithLineNumber(part,
								fullMethodName);
						if (isInWorkspace(classAndLine))
							prepared.add(link(part, classAndLine));
						else
							prepared.add(part);
					} catch (IndexOutOfBoundsException e) {
						System.err
								.println("WARNING: Exception when creating link to java file "
										+ "(StackTraceUtil.linkClasses())");
						prepared.add(part);
					}
				} else {
					prepared.add(part);
				}
				lastpart = part;
			}
			String innerResult = "";
			for (int i = 0; i < prepared.size() - 1; i++) {
				innerResult += prepared.get(i) + "(";
			}
			innerResult += prepared.get(prepared.size() - 1);
			innerResults.add(innerResult);
		}
		String result = "";
		for (int i = 0; i < innerResults.size() - 1; i++) {
			result += innerResults.get(i) + ")";
		}
		result += innerResults.get(innerResults.size() - 1);
		return result;
	}

	public static boolean isClass(String string) {
		return string.matches(".+\\.java:.\\d+");
	}

	private static boolean isInWorkspace(String classAndLine) {
		return true;
		// XXX: Is there a smarter solution for this?
		// return DoodleServer.instance().getJavaFileURL(
		// classAndLine.split(":")[0]) != null;
	}

	/**
	 * Convert from two strings of format
	 * <ul>
	 * <li><code><i>[class]</i>.java:<i>[line]</i></code></li>
	 * <li><code><i>[package]</i>.<i>[class]</i>.<i>[method]</i></code></li>
	 * </ul>
	 * to format<br>
	 * <code><i>[package]</i>.<i>[class]</i>:<i>[line]</i></code>.
	 * 
	 * @param fullMethodName
	 * @return
	 */
	private static String fullClassWithLineNumber(String javaFileAndLineNumber,
			String fullMethodName) {
		String[] parts1 = javaFileAndLineNumber.split(":");
		int lineNumber = Integer.parseInt(parts1[parts1.length - 1]);

		String[] parts2 = fullMethodName.split("\\.");
		String result = "";
		for (int i = 0; i < parts2.length - 2; i++) {
			result += parts2[i] + ".";
		}
		result += parts2[parts2.length - 2];

		result += ":" + lineNumber;
		return result;
	}

	@Deprecated
	private static String link(String part, String fullClassName) {
		return part;
		// "<a href=\"" + DoodleLocationCodes.JAVA_FILE_LINK_PREFIX +
		// fullClassName + "\">" + part + "</a>";
	}

	public static String getStackTraceWithoutCause(Throwable throwable) {
		String result = "";
		StackTraceElement[] trace = throwable.getStackTrace();
		result += throwable.getLocalizedMessage();
		for (StackTraceElement element : trace) {
			result += "\n at " + element;
		}
		return result + "\n";
	}

	public static String getStackTrace(Throwable throwable) {
		Writer writer = new StringWriter();
		PrintWriter printWriter = new PrintWriter(writer);
		throwable.printStackTrace(printWriter);
		return writer.toString();
	}
}
