package ch.unibe.scg.doodle.util;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import org.apache.hadoop.hbase.util.Base64;

public class EncodingUtil {
	private static final Charset CHARSET = StandardCharsets.UTF_8;

	public static String toBase64(String original) {
		return Base64.encodeBytes(original.getBytes(CHARSET));
	}

	public static String toBase64(byte[] bytes) {
		return Base64.encodeBytes(bytes);
	}

	public static String fromBase64(String base64) {
		return new String(Base64.decode(base64), CHARSET);
	}

	public static byte[] bytesFromBase64(String base64) {
		return Base64.decode(base64);
	}
}
