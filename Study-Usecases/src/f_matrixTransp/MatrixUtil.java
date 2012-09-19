package f_matrixTransp;

public class MatrixUtil {
	public static int[][] randomMatrix(int x, int y) {
		int[][] matrix = new int[x][y];
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[0].length; j++) {
				matrix[i][j] = (int) (Math.random() * 10);
			}
		}
		return matrix;
	}

	public static int[][] transpose(int[][] matrix) {
		if (matrix.length == 0 || matrix[0].length == 0)
			return matrix;

		int[][] result = new int[matrix[0].length][matrix.length];
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[0].length; j++) {
				int delta = (int) ((Math.random() * 20 - 10) / 9);
				delta *= (int) (Math.random() * 3);
				result[j][i] = matrix[i][j] + delta;
			}
		}

		return result;
	}
}
