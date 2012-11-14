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
	public static final String PUBLIC_CLASS = org.eclipse.jdt.ui.ISharedImages.IMG_OBJS_INNER_CLASS_PUBLIC;
	public static final String PROTECTED_FIELD = org.eclipse.jdt.ui.ISharedImages.IMG_FIELD_PROTECTED;
	public static final String PROTECTED_METHOD = org.eclipse.jdt.ui.ISharedImages.IMG_OBJS_PROTECTED;
	public static final String PROTECTED_CLASS = org.eclipse.jdt.ui.ISharedImages.IMG_OBJS_INNER_CLASS_PROTECTED;
	public static final String PRIVATE_FIELD = org.eclipse.jdt.ui.ISharedImages.IMG_FIELD_PRIVATE;
	public static final String PRIVATE_METHOD = org.eclipse.jdt.ui.ISharedImages.IMG_OBJS_PRIVATE;
	public static final String PRIVATE_CLASS = org.eclipse.jdt.ui.ISharedImages.IMG_OBJS_INNER_CLASS_PRIVATE;
	public static final String DEFAULT_FIELD = org.eclipse.jdt.ui.ISharedImages.IMG_FIELD_DEFAULT;
	public static final String DEFAULT_METHOD = org.eclipse.jdt.ui.ISharedImages.IMG_OBJS_DEFAULT;
	public static final String DEFAULT_CLASS = org.eclipse.jdt.ui.ISharedImages.IMG_OBJS_INNER_CLASS_DEFAULT;
	
//	public static final String STATIC_MEMBER = org.eclipse.ui.ISharedImages.im
	
	private static final File imgDir = DoodleDebugProperties.tempDirForImages();

	public static File getIcon(String iconName) {
		ISharedImages images = JavaUI.getSharedImages();

		// XXX: Some strange racing condition occurs here...
		Image image = null;
		int count = 0;
		while (image == null) {
			try {
				image = images.getImage(iconName);
			} catch (NullPointerException e) {
				try {
					Thread.sleep(10);
				} catch (InterruptedException e1) {
					throw new RuntimeException(e1);
				}
			}
			count++;
			if (count >= 20)
				return DoodleImages.getLoadingErrorIcon();
		}

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
