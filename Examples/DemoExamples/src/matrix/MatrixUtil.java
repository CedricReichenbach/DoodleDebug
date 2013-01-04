package matrix;

public class MatrixUtil {
	public static float[][] transpose(float[][] matrix) {
		if (matrix.length == 0)
			return matrix;

		float[][] transposed = new float[matrix[0].length][matrix.length];
		for (int x = 0; x < matrix.length; x++)
			for (int y = 0; y < matrix[0].length; y++)
				transposed[y][x] = matrix[x][y];

		return transposed;
	}

	public static float[][] scale(float[][] matrix, float factor) {
		if (matrix.length == 0)
			return matrix;

		float[][] scaled = new float[matrix.length][matrix[0].length];
		for (int x = 0; x < matrix.length; x++)
			for (int y = 0; y < matrix[0].length; y++)
				scaled[x][y] = matrix[x][y] * factor;

		return scaled;
	}
}
