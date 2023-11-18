package pgdp.recursion;

import java.beans.PropertyEditorSupport;
import java.security.Permission;
import java.util.Arrays;
import java.util.stream.Collectors;

public class RecursiveMath {

	// Für Experimente
	public static void main(String... args) {
		char[] word = new char[] {'A', 'B', 'B'};
		System.out.println(differenceAsBs(word));
	}

	// Hilfsmethode zum Printen eines 2D-Arrays. Code muss nicht verstanden werden. Verwende die Methode ad libitum.
	public static String twoDArrayToString(int[][] array) {
		return Arrays.stream(array).map(Arrays::toString).collect(Collectors.joining("\n"));
	}

	/* ================ Binomialkoeffizient und Fibonacci Iterativ und Rekursiv im Vergleich ================ */

	public static long binomCoeffIterative(int n, int k) {
		if (k == 0 || n == k) {
			return 1;
		}
		if (k > n) {
			return 0;
		}
		long result = 1;
		for (int i = 1; i <= k; i++) {
			result *= n + 1 - i;
			result /= i;
		}
		return result;
	}

	public static long binomCoeffRecursive(int n, int k) {
		if (k > n) {
			return 0;
		}

		if (k == 0 || k == n) {
			return 1;
		}

		return binomCoeffRecursive(n - 1, k - 1) + binomCoeffRecursive(n - 1, k);
	}

	public static long fibonacciIterative(int n) {
		if (n <= 0) {
			return 0;
		}
		long last = 1;
		long current = 0;
		long tmp;
		for (int i = 0; i < n; i++) {
			tmp = last + current;
			last = current;
			current = tmp;
		}
		return current;
	}

	public static long fibonacciRecursive(int n) {
		if (n <= 1 && n >= 0) {
			return n;
		}

		return fibonacciRecursive(n - 1) + fibonacciRecursive(n - 2);
	}

	/* ================ Weitere Rekursive Methoden ================ */

	public static int differenceAsBs(char[] word) {
		return differenceAsBs(word, 0);
	}

	public static int differenceAsBs(char[] word, int from) {
		if (from >= word.length) {
			return 0;
		}

		if (word[from] == 'B') {
			return -1 + differenceAsBs(word, from + 1);
		}

		if (word[from] == 'A') {
			return 1 + differenceAsBs(word, from + 1);
		} else {
			return differenceAsBs(word, from + 1);
		}
	}

	public static int[][] permutations(int[] array) {
		return permutations(array, array.length);
	}

	// Heap's recursive algorithm
	public static int[][] permutations(int[] array, int length) {
		if (length == 0) {
			return new int[][] {{}};
		}

		int current = array[length - 1];

		int[][] permutationsBefore = permutations(array, length - 1);

		int[][] allPermutations = new int[permutationsBefore.length * length][];

		int index = 0;

        for (int[] ints : permutationsBefore) {
            for (int[] value : fill(ints, current)) {
                allPermutations[index] = value;
                index++;
            }
        }

		return allPermutations;
	}

	private static int[][] fill(int[] array, int element) {
		int[][] result = new int[array.length + 1][];
		for (int i = 0; i <= array.length; i++) {
			result[i] = insert(array, element, i);
		}
		return result;
	}

	public static int[] insert(int[] array, int element, int position) {
		int[] result = new int[array.length + 1];

		for (int i = 0; i < position; i++) {
			result[i] = array[i];
		}

		result[position] = element;

		for (int i = position; i < array.length; i++) {
			result[i+1] = array[i];
		}
		return result;
	}
}