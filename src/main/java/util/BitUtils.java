package util;

public class BitUtils {
	public static int biggestBitIndex(int n) {
		int ans = -1;
		while (n > 0) {
			ans++;
			n >>= 1;
		}
		return ans;
	}
	
	public static int pow2(int n) {
		return 1 << n;
	}
	
	public static boolean isPowerOfTwo(int n) {
		int amountOfOnes = 0;
		while (n > 0) {
			if (n % 2 == 1) amountOfOnes++;
			if (amountOfOnes > 1) return false;
			n >>= 1;
		}
		return amountOfOnes <= 1;
	}
}
