package image;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

import ch.unibe.scg.doodle.Doo;

public class ImageMain {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		URL url = ImageMain.class.getResource("baby-tux.png");
		BufferedImage image = ImageIO.read(url);
		ImageContact babyTux = new ImageContact("Baby Tux",
				"Finland", image, "+358 9 012 345");
		
		Doo.dle(babyTux);
	}
}
