package pgdp.pengunums;

public class PenguNums {

	/**
	 * Gets the n-th penguin number for positive n
	 * 
	 * @param n the index of the penguin number, positive including zero
	 * @return n-th penguin number
	 */
	public static long penguNumPositive(int n) {
		long penguNum = 2;

		if (n <= 1) {
			return n;
		}

		if (n == 2) {
			return 1;
		}

		long[] penguNums = new long[n + 1];

		penguNums[0] = 0;
		penguNums[1] = 1;
		penguNums[2] = 1;

		for (int i = 3; i < penguNums.length; i++) {
			if (Long.MAX_VALUE - penguNum >= 0) {
				penguNum += penguNums[i - 1] + penguNums[i - 2] + penguNums[i - 3];
			} else {
				System.out.println("Overflow!");
			}
		}

		return penguNum;
	}

	/**
	 * Gets the n-th penguin number for negative n
	 * 
	 * @param n the index of the penguin number, strictly negative
	 * @return n-th penguin number
	 */
	public static long penguNumNegative(int n) {
	    return (n % 2 == 0) ? (-1) * penguNumPositive(n) : penguNumPositive(n);
	}

	/**
	 * Gets the n-th penguin number
	 * 
	 * @param n the index of the penguin number
	 * @return n-th penguin number
	 */
	public static long penguNum(int n) {
	    return (n >= 0) ? penguNumPositive(n) : penguNumNegative(n);
	}

	/**
	 * Gets the index of the given penguin number
	 * 
	 * @param n the penguin number
	 * @return the index of the penguin number
	 */
	public static int penguNumIndex(long n) {
	    if (n >= 0) {
			int index = 0;

            while (penguNum(index) != n) {
                index++;
            }

			return index;
		} else {
			int index = -2;

			while (penguNum(index) != n) {
				index -= 2;
			}

			return index;
		}
	}

	/**
	 * Gets if n is a penguin number for positive n
	 * 
	 * @param n the number that should be checked, positive including zero
	 * @return true if n is penguin number, false otherwise
	 */
	public static boolean isPenguNumPositive(long n) {
		long penguNum = -1;

		if (n == 0 || n == 1 || n == 2) {
			return true;
		}

		long[] penguNums = new long[3];

		int length = 4;

		penguNums[1] = 1;
		penguNums[2] = 1;

		while (penguNum < n) {
			for (int i = 3; i < length; i++) {
				if (Long.MAX_VALUE - penguNum >= 0) {
					penguNum += penguNums[i - 1] + penguNums[i - 2] + penguNums[i - 3];
				} else {
					System.out.println("Overflow!");
				}
			}

			penguNums = append(penguNums, penguNum);
			length++;
		}

		return penguNum == n;
	}

	private static long[] append(long[] array, long penguNum) {
		if (array.length == 0) {
            return new long[] {penguNum};
		}

		long[] res = new long[array.length + 1];

		for (int i = 0; i < array.length; i++) {
			res[i] = array[i];
		}

		res[array.length] = penguNum;

		return res;
	}

	/**
	 * Gets if n is a penguin number
	 * 
	 * @param n the number that should be checked
	 * @return true if n is penguin number, false otherwise
	 */
	public static boolean isPenguNum(long n) {
		if (n % 2 == 0) {
			return (isPenguNumPositive(n) || isPenguNumPositive(-n));
		} else {
			return isPenguNumPositive(n);
		}
	}

}