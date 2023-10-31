package pgdp.math;

import javax.sound.midi.SysexMessage;

public class PinguSqrt {

	public static void sqrt(double n) {
		// Check for negative n
		if (n < 0) {
			System.out.println("Keine negativen Wurzeln!");
		}

		// Sqrt of n and empty line
		System.out.println("Wurzel aus " + n + "\n");

		// Count digits left of decimal point
		int digits_left = 0;
		int aux = (int) n;
		while (aux > 0) {
			aux /= 10;
			digits_left++;
		}

		// Add 0 to the left if odd number of digits left of decimal point
		if (digits_left % 2 == 1) {
			int decimals_left = Integer.parseInt(String.valueOf(0) + aux);
		}

		// Count digits right of decimal point
		String decimalsString = String.valueOf(n).substring(String.valueOf(n).indexOf(".")).
				substring(1);
		int digits_right = decimalsString.length();

		// Add 0 to the right if odd number of decimals
		int decimals = Integer.parseInt(decimalsString);
		if (digits_right % 2 == 1) {
			decimals *= 10;
		}

		// Divide number into groups of 2 digits
		int digits = digits_right + digits_left;

		// Adjust n to accommodate Integer.MAX_VALUE, get rid of decimal point
		long input = ((long) n * (power(digits_right)));

		// <|=================== Loop ===================|>

		// Initialize minuend, subtrahend, result, loop counter for power of 10
		long min, sub = -1, sqrt = 0, rem = 0;

		while (digits > 0) {
			min = (int) (input / power(digits - 2));
			min += rem * 100;

			System.out.println(min);
			System.out.println("--------");

			int counter = 0;

			while (min + sub >= 0) {
				min += sub;
				System.out.println(sub);
				counter++;
				sub -= 2;
			}

			rem = min;

			System.out.println("--------");
			System.out.println("Rest: " + min);
			System.out.println("neue Ergebnis Ziffer: " + counter + "\n");

			input %= power(digits - 1);
			digits -= 2;
			sqrt += counter;
			sqrt *= 10;
			sub = - (2 * sqrt + 1);
		}

		sqrt /= 100;
		System.out.println("Ergebnis: " + sqrt);
	}

	// <|=================== Helpers ===================|>

	private static long power (long exponent) {
		long result = 1;
		while (exponent != 0) {
			result *= 10;
			exponent--;
		}
		return result;
	}

	public static void main(String[] args) {
		// test your implementation here
		sqrt(4);
		sqrt(1049.76);
	}
}
