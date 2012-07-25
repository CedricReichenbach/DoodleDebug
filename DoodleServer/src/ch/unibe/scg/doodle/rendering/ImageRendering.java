package ch.unibe.scg.doodle.rendering;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import ch.unibe.scg.doodle.htmlgen.Tag;
import ch.unibe.scg.doodle.properties.DoodleDebugProperties;

public class ImageRendering implements Rendering<Image> {

	@Override
	public void render(Image image, Tag tag) throws DoodleRenderException {
		File imgDir = DoodleDebugProperties.tempDirForImages();
		File imgFile = new File(imgDir, "/img" + image.hashCode() + ".png");
		try {
			BufferedImage bufferedImage = new BufferedImage(
					image.getWidth(null), image.getHeight(null),
					BufferedImage.TYPE_INT_ARGB);
			Graphics graphics = bufferedImage.getGraphics();
			graphics.drawImage(image, 0, 0, null);
			ImageIO.write(bufferedImage, "png", imgFile);
			Tag imgTag = new Tag("img", "src=" + imgFile.toURI().toString());
			tag.add(imgTag);
		} catch (IllegalArgumentException e) {
			throw new DoodleRenderException(e);
		} catch (IOException e) {
			Tag error = new Tag("div", "class=error");
			error.add("Could not render image: " + e.getMessage());
			tag.add(error);
		}
	}

}
