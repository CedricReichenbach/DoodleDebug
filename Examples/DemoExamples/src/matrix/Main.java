package matrix;

import java.util.ArrayList;
import java.util.List;

import util.SysoutUtil;

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
		Float[][] example1 = { { 2f, 0f, 0f }, { 0f, 2f, 0f } };
		Float[][] example2 = { { 1f, -.707107f, 0f },
				{ .707107f, .707107f, 0f } };
		List<Float[][]> examples = new ArrayList<>();
		examples.add(example1);
		examples.add(example2);
		Doo.dle(examples);

		Float[][] scaleMatrix = { { 3.5f, 0f, 0f }, { 0f, 3.5f, 0f },
				{ 0f, 0f, 3.5f } };
		Float[][] rotationMatrix = {
				{ (float) Math.cos(Math.PI / 3), (float) -Math.sin(Math.PI / 3) },
				{ (float) Math.sin(Math.PI / 3), (float) Math.cos(Math.PI / 3) } };
		Float[][] vector = { { 12f, 33f, 0.1f, 0f, 0f } };

		List<Float[][]> matrices = new ArrayList<Float[][]>();
		matrices.add(scaleMatrix);
		matrices.add(rotationMatrix);
		matrices.add(vector);

		Doo.dle(matrices);
		SysoutUtil.printMatrixList(matrices);

		for (Float[][] matrix : matrices) {
			MatrixUtil.transpose(matrix);
			Doo.dle(matrices);
		}
		// Oh, they didn't change!

		for (int i = 0; i < matrices.size(); i++) {
			Float[][] transposed = MatrixUtil.transpose(matrices.get(i));
			matrices.set(i, transposed);
			Doo.dle(matrices);
		}
		// Now they're transposed.

		for (int i = 0; i < matrices.size(); i++) {
			Float[][] scaled = MatrixUtil.scale(matrices.get(i), 0.1f);
			matrices.set(i, scaled);
			Doo.dle(matrices);
		}
	}

}
