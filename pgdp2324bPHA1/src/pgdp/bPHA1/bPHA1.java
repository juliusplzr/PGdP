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
    
    public static void main(String[] args) {
        int[] testArray = {1, 2, 3, 4, 5, 8, 9};
        System.out.println("Test case: " + Arrays.toString(paritySort(testArray)));
    }
}
