package pgdp.hello;

public class bPHA1 {
    
    // <|=============================== Exercise 1 ===============================|>
    // Sort the array such that even numbers are strictly separated from odd numbers
    // with even numbers being moved into the first half of the array.
    
    public static int[] paritySort(int[] array) {
	        if (array.length == 0) {
			return array;
		}
	    
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
    // sional array. If the matrix is not square, return empty array and print error.

    public static int[][] getAntiDiagonals(int[][] matrix) {
		if (matrix.length == 0) {
			return new int[0][0];
		}

		for (int i = 0; i < matrix.length - 1; i++) {
			if (matrix[i].length != matrix[i + 1].length || matrix.length != matrix[i].length) {
				System.out.println("Matrix must be square!");
				return new int[0][0];
			}
		}

		int[][] antiDiagonals = new int[2 * matrix.length - 1][matrix[0].length];

		for (int diagonalIndexSum = 0; diagonalIndexSum <= 2 * (matrix.length - 1); diagonalIndexSum++) {
			for (int i = 0; i < matrix.length; i++) {
				for (int j = 0; j < matrix[i].length; j++) {
					if (i + j == diagonalIndexSum) {
						antiDiagonals[diagonalIndexSum][j] = matrix[i][j];
					}
				}
			}
		}


		for (int i = 0; i < antiDiagonals.length; i++) {
			if (i < (antiDiagonals.length / 2)) {
				antiDiagonals[i] = removeRange(antiDiagonals[i], i + 1, antiDiagonals[i].length - 1);
			} else {
				antiDiagonals[i] = (i != antiDiagonals.length / 2) ? removeRange(antiDiagonals[i], 0,
						i - (antiDiagonals.length / 2 + 1)) : antiDiagonals[i];
			}
		}

		return antiDiagonals;
	}

	// <|=============================== Helper ===============================|>
	// Removes elements from int array in a given index interval INCLUDING (!) 
	// BOTH lower and upper bound. If index interval is empty or index range invalid
	// return empty array and print error.  

	public static int[] removeRange(int[] array, int start, int end) {
		if (array.length == 0) {
			return new int[0];
		}

		if (start > end) {
			System.out.println("Index range empty!");
			return new int[0];
		}

		if (end > array.length - 1 || start < 0) {
			System.out.println("Invalid index range!");
			return new int[0];
		}

		int[] removed = new int[array.length - (end - start + 1)];

		for (int i = 0; i < start; i++) {
			removed[i] = array[i];
		}

		for (int i = end + 1; i < array.length; i++) {
			removed[i - (end - start + 1)] = array[i];
		}

		return removed;
	}

    // <|=============================== Exercise 3 ===============================|>
    // Extract all distinct elements from int array and return as new int array.
	
    public static int[] distinct(int[] a) {
	        if (a.length == 0) {
			return a;
		}
	    
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

    // <|=============================== Exercise 4 ===============================|>
    /** Zip arrays in 2d arrays, i. e. include one element of each array in order from
     * first to last array.
     *
     * @param arrays Array of Integer-Arrays
     * @return all arrays in arrays zipped together
     */

    public static int[] zipArrays(int[][] arrays){
        int maxLength = 0;
        int sumOfLengths = 0;
	    
        for (int i = 0; i < arrays.length; i++) {
            sumOfLengths += arrays[i].length;
            if (arrays[i].length > maxLength) {
                maxLength = arrays[i].length;
            }
        }

        int[] zippedArray = new int[sumOfLengths];
        int zippedIndex = 0;
	    
        for (int j = 0; j < maxLength; j++) {
            for (int i = 0; i < arrays.length; i++) {
                if (j < arrays[i].length) {
                    zippedArray[zippedIndex] = arrays[i][j];
                    zippedIndex++;
                }
            }
        }

        return zippedArray;
    }

    // <|=============================== Exercise 5 ===============================|>
    /** Given a string array (lower and upper-case letters) of words, return the 
    * maximum value of length(word[i]) * length(word[j]) where the two words do not 
    * share common letters. If no such two words exist, return -1.
    * @param String[] words
    * @return max product of two words with distinct letters
    */

    public static int maxWordProduct(String[] words) {
	if (words.length == 0) {
		return -1;
	}

        for (String word : words) {
		boolean invalid = false;

            for (int j = 0; j < word.length(); j++) {
                if (word.charAt(j) > 'z' || word.charAt(j) < 'A' ||
                        (word.charAt(j) > 90 && word.charAt(j) < 97)) {
                    invalid = true;
                    break;
                }
            }

            if (invalid) {
		return -1;
            }
         }

	int[][] letterCollection = new int[words.length][52];

	for (int i = 0; i < letterCollection.length; i++) {
		for (int j = 0; j < words[i].length(); j++) {
			if (words[i].charAt(j) >= 'a' && words[i].charAt(j) <= 'z') {
				letterCollection[i][words[i].charAt(j) - 'a']++;
			} else {
				letterCollection[i][words[i].charAt(j) - 'A' + 26]++;
			}
		}
	}

	int max = -1;

	for (int i = 0; i < letterCollection.length - 1; i++) {
		int[] word1 = letterCollection[i];

		for (int j = i + 1; j < letterCollection.length; j++) {
			int[] word2 = letterCollection[j];

			boolean sameLetter = false;

			for (int letter = 0; letter < word1.length; letter++) {
				if (word1[letter] > 0 && word2[letter] > 0) {
					sameLetter = true;
					break;
				}
			}

			if (!sameLetter) {
				int prod = words[i].length() * words[j].length();
				max = (prod > max) ? prod : max;
			}
		}
	}

	return max;
    }

    // <|=============================== Exercise 6 ===============================|>
    /** Given an integer array nums, reorder it in-place such that nums[0] < nums[1] 
    * > nums[2] < nums[3]... You may assume the input array always has a valid answer.
    * @param int[] nums
    */

    public static void wiggleSort(int[] array) {
		int length = array.length;

		if (length == 0 || length == 1) {
			System.out.println("Array already sorted!");
			return;
		}

		int temp;
		for (int i = 0; i < array.length; i++) {
			for (int j = i; j < array.length; j++) {
				if (array[i] > array[j]) {
					temp = array[j];
					array[j] = array[i];
					array[i] = temp;
				}
			}
		}

		int median;
		if (length % 2 != 0) {
			median = array[length / 2];
		} else {
			median = (array[length / 2] + array[(length / 2) - 1]) / 2;
		}

		int largePos = 1;
		int smallPos = (length % 2 == 0) ? length - 2 : length - 1;
		int currentPos = 0;

		while (currentPos < length) {
			int aux;
			if (array[currentPos] < median && (currentPos < smallPos || (currentPos % 2 != 0))) {
				aux = array[smallPos];
				array[smallPos] = array[currentPos];
				array[currentPos] = aux;
				smallPos -= 2;
			} else if (array[currentPos] > median && (currentPos > largePos || (currentPos % 2 == 0))) {
				aux = array[largePos];
				array[largePos] = array[currentPos];
				array[currentPos] = aux;
				largePos += 2;
			} else {
				currentPos++;
			}
		}
	}
    
	

    // <|=============================== Main ===============================|>
    // Pro tip: Your implementation won't fail if you don't test it in the first
    // place.
	
    public static void main(String[] args) {
        int[] testArray = {1, 2, 3, 4, 5, 8, 9};
        System.out.println("Test case: " + Arrays.toString(paritySort(testArray)));
    }
}
