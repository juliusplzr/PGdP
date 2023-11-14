package pgdp.hello;

public class bPHA1 {
    
    // <|=============================== Exercise 1 ===============================|>
    // Sort the array such that even numbers are strictly separated from odd numbers
    // with even numbers being moved into the first half of the array.
    
    public static int[] paritySort(int[] array) {
		for (int i = 0; i < array.length; i++) {
			if (array[i] % 2 != 0) {
				for (int j = i + 1; j < array.length; j++) {
					int temp;
					if (array[j] % 2 == 0) {
						temp = array[i];
						array[i] = array[j];
						array[j] = temp;
					}
				}
			}
		}

		return array;
    }

    // <|=============================== Exercise 2 ===============================|>
    // Extract the anti-diagonals in a square int matrix and return as two-dimen-
    // sional array.

    public static int[][] getAntiDiagonals(int[][] matrix) {
		return new int[0][0];
    }

    // <|=============================== Exercise 3 ===============================|>
    // Extract all distinct elements from int array and return as new int array.
	
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
    
    public static void main(String[] args) {
        int[] testArray = {1, 2, 3, 4, 5, 8, 9};
        System.out.println("Test case: " + Arrays.toString(paritySort(testArray)));
    }
}
