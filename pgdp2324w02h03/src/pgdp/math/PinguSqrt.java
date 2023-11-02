package pgdp.math;


public class PinguSqrt {

	public static void sqrt(double n) {
		// Check for negative n
		if (n < 0) {
			System.out.println("Keine negativen Wurzeln!");
			return;
		}

		// Print headline
		System.out.println("Wurzel aus " + n + "\n");

		// Count digits left of decimal point,
		int digits_left = 0;
		long temp = (long) n;

		while (temp > 0) {
			temp /= 10;
			digits_left++;
		}

		// Count digits right of decimal point
		String decimalsString = String.valueOf(n).substring(String.valueOf(n).indexOf(".")).
				substring(1);
		int digits_right = decimalsString.length();

		// Total digit count
		int digits = digits_left + digits_right;

		// Get rid of decimals
		long input = (long) (n * (power(digits_right)));

		// <|=================== Loop ===================|>

		// Initialize loop variables
		long min;
		long sub;
		int counter;
		long sqrt = 0;
		long rem = 0;
		int iter = 0;

		// Main loop
		while (digits > 0) {
			// Loop iteration counter
            		iter++;

			// Obtain digit pair from input
			if (iter == 1 && digits_left % 2 == 1) {
				min = (int) (input / power(digits - 1));
			} else {
				min = (int) (input / power(digits - 2));
			}

			// Calculate new minuend by adding remainder * 100 and digit pair
			min += rem * 100;

			// Update subtrahend
			sub = - (2 * sqrt + 1);

			// Print minuend
			System.out.println(min);
			System.out.println("--------");

			// Reset counter for subtraction loop
			counter = 0;

			// Subtraction loop
			while (min + sub >= 0) {
				min += sub;
				System.out.println(sub);
				counter++;
				sub -= 2;
			}

			// Save remainder
			rem = min;

			// Print remainder and sqrt digit
			System.out.println("--------");
			System.out.println("Rest: " + rem);
			System.out.println("neue Ergebnis Ziffer: " + counter + "\n");

			// Decrease digit count by two
			digits -= 2;

			// Delete processed digits
			input %= power(digits);

			// Add next sqrt digit
			sqrt += counter;
			sqrt *= 10;
		}

			// Convert to double, precision of two decimals, print result
			double res;
			if (digits_left == 1) {
				res = (double) sqrt / power(1);
			} else {
				res = (double) sqrt / power(digits_left / 2);
			}

			System.out.println("Ergebnis: " + res);
		}

	// <|=================== Helpers ===================|>

	// Calculate power of 10
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
		sqrt(4.0);
	}
}
