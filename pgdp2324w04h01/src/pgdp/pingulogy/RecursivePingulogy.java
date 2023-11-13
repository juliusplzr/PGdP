package pgdp.pingulogy;

public class RecursivePingulogy {

	// task 1

	public static long pinguSequenceRec(int n, int p0, int p1, int p2) {
		int memoArraySize = 145 - (-122) + 1;
		long[] memoArray = new long[memoArraySize];
		boolean[] calculatedPrev = new boolean[memoArraySize];
		return pinguSequenceRec(n , p0, p1, p2, memoArray, calculatedPrev);
	}

	public static long pinguSequenceRec(int n, int p0, int p1, int p2, long[] memoArray, boolean[] calculatedPrev) {
		int index = n + 122;
		if (n == 0) {
			return p0;
		} else if (n == 1) {
			return p1;
		} else if (n == 2) {
			return p2;
		} else if (n < 0) {
			if (calculatedPrev[index]) {
				return memoArray[index];
			}
			long result = 2 * pinguSequenceRec(-n, p0, p1, p2, memoArray, calculatedPrev);
			memoArray[index] = result;
			calculatedPrev[index] = true;
			return result;
		} else {
			if (calculatedPrev[index]) {
				return memoArray[index];
			}
			long result = pinguSequenceRec(n - 1, p0, p1, p2, memoArray, calculatedPrev)
					- pinguSequenceRec(n - 2, p0, p1, p2, memoArray, calculatedPrev)
					+ 2 * pinguSequenceRec(n - 3, p0, p1, p2, memoArray, calculatedPrev);
			memoArray[index] = result;
			calculatedPrev[index] = true;
			return result;
		}
	}

	// task 2
	// Hint: pinguF and pinguM are not static (and must not be changed to it!)
	// more information in the main-method below
	public int pinguF(int n) {
		if (n == 0) {
			return 1;
		} else {
			return n - pinguM(pinguF(--n));
		}
	}

	public int pinguM(int n) {
		if (n == 0) {
			return 0;
		} else {
			return n - pinguF(pinguM(--n));
		}
	}

	// task 3
	public static int pinguCode(int n, int m) {
		return pinguCodeHelp(n, m, 0);
	}

	private static int pinguCodeHelp(int n, int m, int intermediateResult) {
		if (n == 0) {
			return m + intermediateResult;
		}

		if ((n + intermediateResult) % 2 == 0) {
			return pinguCodeHelp(m, n/2, intermediateResult + n / 2);
		} else {
			return pinguCodeHelp(n - 1, m/2, intermediateResult + m);
		}
	}

	// task 4
	public static String pinguDNA(int f, int m) {
		if (f == 0) {
			if (m == 0) {
				return "";
			}
			return pinguDNA(f, m >>> 1) + "A";
		} else if (m == 0) {
			return pinguDNA(f >>> 1 , m) + "T";
		} else {
			if (f % 2 == 0 && m % 2 == 0 || f % 2 == 1 && m % 2 == 1) {
				if (f > m) {
					return pinguDNA(f >>> 1, m >>> 1) + "GT";
				} else if (f < m) {
					return pinguDNA(f >>> 1, m >>> 1) + "GA";
				} else {
					return pinguDNA(f >>> 1, m >>> 1) + "GC";
				}
			} else {
				if (f % 2 == 1) {
					return pinguDNA(f >>> 1, m >>> 1) + "TC";
				}
				return pinguDNA(f >>> 1, m >>> 1) + "AC";
			}
		}
	}

	public static void main(String[] args) {
		// switch value to test other tasks
		int testTask = 1;

		switch (testTask) {
		case 1:
			System.out.println("Task 1 example output");
			for (int i = 0; i < 145; i++) {
				System.out.println(i + ": " + pinguSequenceRec(i, 1, 1, 2));
			}
			break;
		case 2:
			/**
			 * For better testing, pinguF and pinguM are not static. 
			 * Hence, you have to initialize a new RecursivePingulogy Object and 
			 * call the methods on that instance, as you can see below.
			 * You will learn more about object-oriented-programming in the lecture
			 * and week 05+.
			 */
			RecursivePingulogy rp = new RecursivePingulogy();
			System.out.print("Task 2 example output\npinguF: ");
			for (int i = 0; i < 10; i++) {
				System.out.print(rp.pinguF(i) + ", ");
			}
			System.out.print("\npingM: ");
			for (int i = 0; i < 10; i++) {
				System.out.print(rp.pinguM(i) + ", ");
			}
			break;
		case 3:
			System.out.println("Task 3 example output");
			for (int i = 0; i < 10; i++) {
				for (int j = 0; j < 10; j++) {
					System.out.println(i + ", " + j + ": " + pinguCode(i, j));
				}
				System.out.println("----------");
			}
			break;
		case 4:
			System.out.println("Task 4 example output");
			System.out.println("pinguDNA(26197, 9363) = " + pinguDNA(26197, 9363));
			break;
		default:
			System.out.println("There are only 4 tasks!");
			break;
		}
	}
}
