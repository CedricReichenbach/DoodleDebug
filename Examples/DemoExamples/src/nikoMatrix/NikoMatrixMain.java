package nikoMatrix;

import ch.unibe.scg.doodle.Doo;

public class NikoMatrixMain {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		float[][] matA = { { 2f, 0, 0 }, { 0, 2, 0 } };
		float[][] matB = { { 0.707107f, -0.707107f, 0 }, { 0.707107f, 0.707107f, 0 } };
		
		Object[] array = {matA, matB};
		Doo.dle(array);
	}

}
