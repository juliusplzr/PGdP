package pgdp.warmup;

public class PenguWarmup {

	protected PenguWarmup() {
		throw new UnsupportedOperationException();
	}

	public static void penguInfoOut(int penguin) {
		if (penguin < 0) {
			System.out.println("Penguin " + penguin + " is not a known penguin!");
		} else if (penguin % 2 == 1) {
			System.out.println("Penguin: " + penguin);
			System.out.println("This penguin is a female.");
		} else {
			System.out.println("Penguin: " + penguin);
			System.out.println("This penguin is a male.");
		}
	}

	public static int penguEvolution(int penguin, int years) {

		boolean svn_yrs = false;
		boolean powOfTwo = false;
		int n = 1;

		for (int i = 0; i < years; i++) {

			if (penguin % 2 == 0) {
				while (penguin > n) {
					n = 2 * n;
				}

				if (n - penguin == 0) {
					powOfTwo = true;
				}

				if (powOfTwo) {
					penguin = 1;
				} else {
					penguin /= 2;
				}
			}

			else if (penguin % 7 == 0) {
				if (!svn_yrs) {
					i += 5;
				} else {
					penguin = 3 * penguin + 1;
				}
				svn_yrs = !svn_yrs;
			} else {
				penguin = 3 * penguin + 1;
			}
		}
		return penguin;
	}

	public static int penguSum(int penguin) {
		int sum = 0;

		while (penguin != 0) {
			sum = sum + (penguin % 10);
			penguin = penguin / 10;
		}

		return sum;
	}

	public static long penguPermutation(long n, long k) {
		long max = n > k ? n : k;
		long min = n < k ? n : k;

		long fac = 1;

		for (long i = min + 1; i <= max; i++) {
			fac = fac * i;
		}

		return fac;
	}

	public static long penguPowers(int x, int i) {
		long pow = 1;

		for (int k = 0; k < i; k++) {
			long powInner = 0;
			int aux = x;

			for (int j = 0; j < aux; j++) {
				powInner += pow;
			}

			pow = powInner;
		}

		return pow;
	}

	/*	Die Inhalte der main()-Methode beeinflussen nicht die Bewertung dieser Aufgabe
	 *	(es sei denn natürlich, sie verursachen Compiler-Fehler).
	 */
	public static void main(String[] args) {

		// Here is a place for you to play around :(

		System.out.println(penguPowers(46341, 2));
		System.out.println("2147488281");
	}

}
