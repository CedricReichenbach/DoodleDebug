package util;

import java.util.Collection;

public class SysoutUtil {
	public static void printMatrix(Object[][] matrix) {
		System.out.println("_");
		for (Object[] array : matrix) {
			System.out.print("|");
			for (Object value : array)
				System.out.print(value + "\t");
			System.out.println("|");
		}
		System.out.println("`");
	}

	public static void printList(Collection<?> collection) {
		System.out.print("[ ");
		for (Object value : collection)
			System.out.print(value + ",\t");
		System.out.println(" ]");
	}

	public static void printMatrixList(
			Collection<? extends Object[][]> collection) {
		System.out.println("*** Start of list");
		for (Object[][] matrix : collection) {
			printMatrix(matrix);
			System.out.println("-----");
		}
		System.out.println("*** End of list");
	}
}
