package ch.unibe.scg.doodle.server.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import org.eclipse.jdt.ui.ISharedImages;
import org.eclipse.jdt.ui.JavaUI;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.ImageLoader;

import ch.unibe.scg.doodle.properties.DoodleDebugProperties;

public class EclipseIconUtil {

	public static final String PUBLIC_FIELD = org.eclipse.jdt.ui.ISharedImages.IMG_FIELD_PUBLIC;
	public static final String PUBLIC_METHOD = org.eclipse.jdt.ui.ISharedImages.IMG_OBJS_PUBLIC;
	private static final File imgDir = DoodleDebugProperties.tempDirForImages();

	public static File getIcon(String iconName) {
		ISharedImages images = JavaUI.getSharedImages();
		Image image = images.getImage(iconName);
		File imgFile = new File(imgDir, "/img" + image.hashCode() + ".png");
		createFile(image, imgFile);
		return imgFile;
	}

	private static void createFile(Image image, File file) {
		ImageLoader imgLoader = new ImageLoader();
		imgLoader.data = new ImageData[] { image.getImageData() };
		FileOutputStream stream;
		try {
			stream = new FileOutputStream(file);
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}
		imgLoader.save(stream, SWT.IMAGE_PNG);
	}
}
