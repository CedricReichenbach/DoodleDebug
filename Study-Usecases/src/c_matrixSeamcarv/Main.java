package c_matrixSeamcarv;


public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		int[][][] image = ImageUtil.loadImage();
		SeamCarving.carveSeam(image);
	}

}
