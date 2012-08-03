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
					String[] stuff = lastpart.split("at ");
					String fullClassName = stuff[stuff.length - 1];
					prepared.add(link(part, fullClassName));
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

	private static String link(String part, String fullClassName) {
		return "<a href=\"javafile:" + fullClassName + "\">" + part + "</a>";
	}

	public static String getStackTrace(Throwable throwable) {
		Writer writer = new StringWriter();
		PrintWriter printWriter = new PrintWriter(writer);
		throwable.printStackTrace(printWriter);
		return writer.toString();
	}
}
