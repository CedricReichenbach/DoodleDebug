package c_matrixSeamcarv;

public class ImageUtil {

	/**
	 * Returns an image represented as X*Y*3-array of r-g-b-values from 0 to
	 * 255.
	 * 
	 * @return
	 */
	public static int[][][] loadImage() {
		int[][][] image = new int[40][24][3];
		for (int i = 0; i < image.length; i++) {
			for (int j = 0; j < image[0].length; j++) {
				for (int n = 0; n < image[0][0].length; n++) {
					image[i][j][n] = (int) (Math.random() * 255);
				}
			}
		}
		return image;
	}
}
