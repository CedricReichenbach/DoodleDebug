package matrix;

public class MatrixUtil {
	public static Float[][] transpose(Float[][] matrix) {
		if (matrix.length == 0)
			return matrix;

		Float[][] transposed = new Float[matrix[0].length][matrix.length];
		for (int x = 0; x < matrix.length; x++)
			for (int y = 0; y < matrix[0].length; y++)
				transposed[y][x] = matrix[x][y];

		return transposed;
	}

	public static Float[][] scale(Float[][] matrix, Float factor) {
		if (matrix.length == 0)
			return matrix;

		Float[][] scaled = new Float[matrix.length][matrix[0].length];
		for (int x = 0; x < matrix.length; x++)
			for (int y = 0; y < matrix[0].length; y++)
				scaled[x][y] = matrix[x][y] * factor;

		return scaled;
	}
}
