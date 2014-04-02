package ch.unibe.scg.doodle;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.inject.Singleton;

import org.apache.hadoop.hbase.util.Base64;

import ch.unibe.scg.doodle.hbase.PersistentIndexedStorage;

@Singleton
public class ImageManager {

	private final PersistentIndexedStorage storage;
	private static final String TABLE_NAME = "raw_images";

	/** Entry 0 is used for persistence. */

	protected ImageManager() {
		this.storage = new PersistentIndexedStorage(TABLE_NAME);
	}

	public int store(Image image) throws IOException {
		return storage.store(asBase64(image));
	}

	private String asBase64(Image image) throws IOException {
		BufferedImage bufferedImage = new BufferedImage(image.getWidth(null),
				image.getHeight(null), BufferedImage.TYPE_INT_ARGB);
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ImageIO.write(bufferedImage, "PNG", out);
		byte[] bytes = out.toByteArray();

		return Base64.encodeBytes(bytes);
	}
}
