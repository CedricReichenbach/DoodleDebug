package e_decAlignment;

public class DatabaseUtil {
	public static float[][] getData() {
		float[][] data = new float[10][4];
		for (int i = 0; i < data.length; i++) {
			for (int j = 0; j < data[0].length; j++) {
				int mul = (int) (Math.random() * 10000);
				data[i][j] = roundDec((float) (Math.random() * mul - mul / 2),
						(int) (Math.random() * 10));
			}
		}
		for (int j = 0; j < data[0].length; j++) {
			data[2][j] = data[7][j];
			data[5][j] = data[8][j];
		}
		return data;
	}

	private static float roundDec(float num, int dec) {
		return (float) Math.round(num * (10 ^ dec)) / (10 ^ dec);
	}
}
