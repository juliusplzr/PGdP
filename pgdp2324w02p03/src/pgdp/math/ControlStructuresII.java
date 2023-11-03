package pgdp.math;

public class ControlStructuresII {
	public static void main(String[] args) {
		// Zum Testen
		System.out.println(threeAndSeven(92));
	}

	public static int threeAndSeven(int n) {
		if (n < 0) {
			System.out.println("Eingabe muss größer oder gleich 0 sein!");
			return -1;
		}
		int sum = 0;
		int i = 0;

		while (i <= n) {
			sum += (i % 3 == 0 || i % 7 == 0) ? i : 0;
			i++;
		}
		return sum;
	}

	public static void printAsciiCodesFor(char start, int count) {
		int ascii = (int) start;
		int iteration = 0;
		while (iteration < count) {
			char ch = (char) ascii;
			System.out.println("Der ASCII-Code von '" + ch + "' ist " + ascii + ".");
			iteration++;
			ascii++;
		}
	}

	public static void printMultiplicationTable(int n) {
		System.out.print("*\t|\t");
		for (int i = 1; i <= n; i++) {
			System.out.print(i + "\t");
		}
		System.out.print("\n");

		for (int i = 1; i <= n+2; i++) {
			System.out.print("----");
		}

		System.out.print("\n");

		for (int i = 1; i <= n; i++) {
			System.out.print(i + "\t|\t");
			for (int j = 1; j <= n; j++) {
				System.out.print(i*j + "\t");
			}
			System.out.println();
		}

	}

	public static void printPrimesUpTo(int n) {
		int i = 3;
		boolean prime = true;

		if (n < 2) {
			return;
		} else {
			System.out.print("2");
		}

		while (i <= n) {
			for (int j = 2; j < i; j++) {
                if (i % j == 0) {
                    prime = false;
                }
			}

			if (prime) {
				System.out.print(" " + i);
			}
			i++;
			prime = true;
		}
	}
}