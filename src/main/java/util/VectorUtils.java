package util;

import java.util.Arrays;

public class VectorUtils {
	public static int[][] deepCopy(int[][] original) {
		if (original == null) {
			return null;
		}

		final int[][] result = new int[original.length][];
		for (int i = 0; i < original.length; i++) {
			result[i] = Arrays.copyOf(original[i], original[i].length);
			// For Java versions prior to Java 6 use the next:
			// System.arraycopy(original[i], 0, result[i], 0, original[i].length);
		}
		return result;
	}

	// Implementing Fisher–Yates shuffle
	public static void shuffleArray(int[] ar) {
		// If running on Java 6 or older, use `new Random()` on RHS here
		for (int i = ar.length - 1; i > 0; i--) {
			int index = RandomUtils.randomBetween(0, i);
			// Simple swap
			int a = ar[index];
			ar[index] = ar[i];
			ar[i] = a;
		}
	}
}
