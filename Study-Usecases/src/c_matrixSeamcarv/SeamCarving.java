package c_matrixSeamcarv;

public class SeamCarving {

	/**
	 * Determines a candidate seam to be cut out.
	 * 
	 * @param image
	 */
	public static void carveSeam(int[][][] image) {
		if (image.length == 0 || image[0].length == 0)
			throw new RuntimeException("Seriously?");

		if (image.length < 5) {
			for (int i = 0; i < image.length; i++) {
				int[] black = { 0, 0, 0 };
				image[i][0] = black;
			}
			return;
		}

		int last = 0;
		boolean back = false;
		for (int i = 0; i < image.length; i++) {
			int[] black = { 0, 0, 0 };
			image[i][last] = black;
			if (back)
				last--;
			else
				last++;
			if (last == image[0].length) {
				back = true;
				last -= 2;
			}
			if (last == -1) {
				back = false;
				last += 2;
			}
		}
		return;
	}
}
