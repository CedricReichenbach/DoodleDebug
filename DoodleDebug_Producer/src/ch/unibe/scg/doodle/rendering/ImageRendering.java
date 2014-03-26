package ch.unibe.scg.doodle.rendering;

import java.awt.Image;

import ch.unibe.scg.doodle.htmlgen.Tag;

public class ImageRendering implements Rendering<Image> {

	@SuppressWarnings("unchecked")
	@Override
	public void render(Image image, Tag tag) throws DoodleRenderException {
		Tag notSupportedMessage = new Tag("div", "class=error");
		notSupportedMessage.add("Images are not supported at the moment.");
		tag.add(notSupportedMessage);
		// TODO: Find solution for images

		// File imgDir = DoodleDebugProperties.tempDirForImages();
		// File imgFile = new File(imgDir, "/img" + image.hashCode() + ".png");
		// try {
		// BufferedImage bufferedImage = new BufferedImage(
		// image.getWidth(null), image.getHeight(null),
		// BufferedImage.TYPE_INT_ARGB);
		// Graphics graphics = bufferedImage.getGraphics();
		// graphics.drawImage(image, 0, 0, null);
		// ImageIO.write(bufferedImage, "png", imgFile);
		// Tag imgTag = new Tag("img", "src=" + imgFile.toURI().toString());
		// tag.add(imgTag);
		// } catch (IllegalArgumentException e) {
		// throw new DoodleRenderException(e);
		// } catch (IOException e) {
		// Tag error = new Tag("div", "class=error");
		// error.add("Could not render image: " + e.getMessage());
		// tag.add(error);
		// }
	}

	@Override
	public void renderSimplified(Image image, Tag tag)
			throws DoodleRenderException {
		this.render(image, tag); // different css only
	}

}
