package ch.unibe.scg.doodle.hbase;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.inject.Singleton;

import org.apache.hadoop.hbase.util.Base64;

import ch.unibe.scg.doodle.plugins.ImagePlugin;
import ch.unibe.scg.doodle.util.ApplicationUtil;

@Singleton
public class ImageManager {

	private final PersistentIndexedStorage storage;
	private static final String TABLE_NAME = "raw_images";

	/** Entry 0 is used for persistence. */

	protected ImageManager() {
		this.storage = new PersistentIndexedStorage(ApplicationUtil.getApplicationName(), TABLE_NAME);
	}

	public int store(Image image) throws IOException {
		return storage.store(asBase64(scale(image)));
	}

	private Image scale(Image image) {
		int width = image.getWidth(null), height = image.getHeight(null);
		if (width > height)
			return image.getScaledInstance(ImagePlugin.MAX_SIDE_LENGTH, -1,
					Image.SCALE_SMOOTH);
		else
			return image.getScaledInstance(-1, ImagePlugin.MAX_SIDE_LENGTH,
					Image.SCALE_SMOOTH);
	}

	private String asBase64(Image image) throws IOException {
		BufferedImage bufferedImage = new BufferedImage(image.getWidth(null),
				image.getHeight(null), BufferedImage.TYPE_INT_ARGB);
		Graphics graphics = bufferedImage.getGraphics();
		graphics.drawImage(image, 0, 0, null);
		graphics.dispose();
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ImageIO.write(bufferedImage, "PNG", out);
		byte[] bytes = out.toByteArray();

		return Base64.encodeBytes(bytes, Base64.DONT_BREAK_LINES);
	}

	/**
	 * Loads an image from storage.
	 * 
	 * @param index
	 * @return Base64 representation of image
	 */
	public String load(int index) {
		return storage.load(index).toString();
	}
}
