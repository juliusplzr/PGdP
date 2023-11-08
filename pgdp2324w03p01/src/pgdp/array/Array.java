package pgdp.array;

import java.util.concurrent.LinkedTransferQueue;

public class Array {
	public static void print(int[] a) {
		System.out.print("{");
		for (int i = 0; i < a.length; i++) {
			if (i != a.length - 1) {
				System.out.print(a[i] + ", ");
			} else {
				System.out.print(a[i]);
			}
		}
		System.out.print("}");
	}

	public static void minAndMax(int[] a) {
		int min = Integer.MAX_VALUE;
		int max = Integer.MIN_VALUE;

        for (int j : a) {
            if (j < min) {
                min = j;
            }
            if (j > max) {
                max = j;
            }
        }

		System.out.println("Minimum = " + min + ", Maximum = " + max);
	}

	public static boolean isOrderedAscendingly(int[] a) {
		boolean ordered = true;

		for (int i = 1; i < a.length; i++) {
			if (a[i - 1] > a[i]) {
				ordered = false;
				break;
			}
		}
		return ordered;
	}

	public static void invert(int[] a) {
		int maxIndex = a.length - 1;

		for (int i = 0; i < a.length / 2; i++) {
			int temp = a[i];
			a[i] = a[maxIndex - i];
			a[maxIndex - i] = temp;
		}
	}

	public static int[] resize(int[] a, int length) {
		if (length <= 0) {
			return new int[0];
		}

		int[] resize = new int[length];
		for (int i = 0; i < length; i++) {
			if (i < a.length) {
				resize[i] = a[i];
			} else {
				resize[i] = 0;
			}
		}

		return resize;
	}

	public static int[] filterEvenNumbersFrom(int[] a) {
		int evenCount = 0;

        for (int j : a) {
            if (j % 2 == 0) {
                evenCount++;
            }
        }

		int[] even = new int[evenCount];

		int evenPos = 0;
		for (int i = 0; i < a.length; i++) {
			if (a[i] % 2 == 0 && evenPos < evenCount) {
				even[evenPos] = a[i];
				evenPos++;
			}
		}

		return even;
	}

	public static int[] distinct(int[] a) {
		int distinctCount = 0;

		for (int i = 0; i < a.length; i++) {
			boolean included = false;
			for (int j = 0; j < i; j++) {
				if (a[j] == a[i]) {
					included = true;
					break;
				}
			}

			if (!included) {
				distinctCount++;
			}
		}

		int[] distinct = new int[distinctCount];

		int pos = 0;

		for (int i = 0; i < a.length; i++) {
			boolean included = false;

			for (int j = 0; j < i; j++) {
				if (a[i] == a[j]) {
					included = true;
					break;
				}
			}

			if (!included && pos < distinctCount) {
				distinct[pos] = a[i];
				pos++;
			}
		}

		return distinct;
	}
}
