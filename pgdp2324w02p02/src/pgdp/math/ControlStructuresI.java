package pgdp.math;

public class ControlStructuresI {
	public static void main(String[] args) {
		// test your implementation here
	}

	public static void printCollatz(int n) {
		if (n <= 0) {
			System.out.println("Eingabe muss größer als 0 sein!");
			return;
		}
		int length = 1;
		System.out.print(n);
		while (n != 1) {
			n = (n % 2 == 0) ? n / 2 : (3 * n + 1);
			System.out.print(" " + n);
			length++;
		}
		System.out.println("\n" + "Länge: " + length);
	}

	public static void printPowersOfTwoUpTo(int n) {
		if (n <= 0) {
			System.out.println("Eingabe muss größer als 0 sein!");
			return;
		}
		int tpow = 1;
		System.out.print(tpow);
		while (2 * tpow <= n) {
			tpow *= 2;
			System.out.print(" " + tpow);
		}
		System.out.print("\n");
	}

	public static void printTriangle(int sideLength) {
		if (sideLength <= 0) {
			System.out.println("Eingabe muss größer als 0 sein!");
			return;
		}
		int i = sideLength;
		while (i > 0) {
			for (int j = 0; j < i; j++) {
				System.out.print("*");
			}
			System.out.print("\n");
			i--;
		}
	}

	public static int calculateNumberOfDigits(int n) {
		if (n < 0) {
			System.out.println("Eingabe muss größer als 0 sein!");
			return -1;
		}
		int digits = 0;
		while (n != 0) {
			n /= 10;
			digits++;
		}
		return digits;
	}

	public static int reverseNumber(int n) {
		int n_rev = 0; int digit;

		while (n > 0) {
			digit = n % 10;
			n_rev *= 10;
			n_rev += digit;
			n /= 10;
		}

		return n_rev;
	}

	public static boolean isPalindrome(int n) {
		return reverseNumber(n) == n;
	}
}