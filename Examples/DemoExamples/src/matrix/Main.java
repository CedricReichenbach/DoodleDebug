package matrix;

import java.util.ArrayList;
import java.util.List;

import ch.unibe.scg.doodle.Doo;

public class Main {

	public static void main(String[] args) {
		try {
			playWithMatrices();
		} catch (Throwable t) { // XXX: Just a gimmick to prevent eclipse view
								// change, but may be removed for demo.
			Doo.dle(t);
		}
	}

	private static void playWithMatrices() {
		float[][] scaleMatrix = { { 2, 0 }, { 0, 2 } };
		float[][] rotationMatrix = {
				{ (float) Math.cos(Math.PI / 3), (float) -Math.sin(Math.PI / 3) },
				{ (float) Math.sin(Math.PI / 3), (float) Math.cos(Math.PI / 3) } };
		float[][] vector = { { 12, 33, 0.1f, 0, 0 } };

		List<float[][]> matrices = new ArrayList<float[][]>();
		matrices.add(scaleMatrix);
		matrices.add(rotationMatrix);
		matrices.add(vector);

		Doo.dle(matrices);

		for (float[][] matrix : matrices) {
			MatrixUtil.transpose(matrix);
			Doo.dle(matrices);
		}
		// Oh, they didn't change!

		for (int i = 0; i < matrices.size(); i++) {
			float[][] transposed = MatrixUtil.transpose(matrices.get(i));
			matrices.set(i, transposed);
			Doo.dle(matrices);
		}
		// Now they're transposed.

		for (int i = 0; i < matrices.size(); i++) {
			float[][] scaled = MatrixUtil.scale(matrices.get(i), 0.1f);
			matrices.set(i, scaled);
			Doo.dle(matrices);
		}
	}

}
