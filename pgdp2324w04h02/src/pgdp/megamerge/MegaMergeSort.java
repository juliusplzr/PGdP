package pgdp.megamerge;

import java.util.Arrays;

public class MegaMergeSort {

	/**
	 * Sorts the array using mega merge sort with div splits
	 * @param array the array to be sorted
	 * @param div the split factor
	 * @return the sorted array
	 */
	protected int[] megaMergeSort(int[] array, int div) {
		return megaMergeSort(array, div, 0, array.length);
	}

	/**
	 * Sorts the array using mega merge sort with div splits in the defined range
	 * @param array the array to be sorted
	 * @param div the split factor
	 * @param from the lower bound (inclusive)
	 * @param to the upper bound (exclusive)
	 * @return the sorted array
	 */
	protected int[] megaMergeSort(int[] array, int div, int from, int to) {
		if (to - from < 1) {
			return new int[0];
		}

		if (to - from == 1) {
			return new int[] {array[from]};
		}

		int elemPerLevel = (to - from) / div;
		int rem = (to - from) % div;

		int start = from;

		int[] sorted;

		int length = elemPerLevel == 0 ? rem : div;
		int[][] results = new int[length][];

		for (int i = 0; i < length; i++) {
			int end = start + elemPerLevel + (i < rem ? 1 : 0);

			results[i] = megaMergeSort(array, div, start, end);

			start = end;
		}

		sorted = merge(results, 0, results.length);

		return sorted;
	}

	/**
	 * Merges all arrays in the given range
	 * @param arrays to be merged
	 * @param from lower bound (inclusive)
	 * @param to upper bound (exclusive)
	 * @return the merged array
	 */
	protected int[] merge(int[][] arrays, int from, int to) {
		if (to <= from) {
			return new int[0];
		}

		return merge(arrays[from], merge(arrays, from + 1, to));
	}

	/**
	 * Merges the given arrays into one
	 * @param arr1 the first array
	 * @param arr2 the second array
	 * @return the resulting array
	 */
	protected int[] merge(int[] arr1, int[] arr2) {
		int indexMerged = 0;

		if (arr1.length == 0) {
			return arr2;
		}

		if (arr2.length == 0) {
			return arr1;
		}

		int[] merged = new int[arr1.length + arr2.length];

		int indexFirst = 0;
		int indexSecond = 0;

		while (indexFirst < arr1.length && indexSecond < arr2.length) {
			if (arr1[indexFirst] < arr2[indexSecond]) {
				merged[indexMerged] = arr1[indexFirst];
				indexFirst++;
			} else {
				merged[indexMerged] = arr2[indexSecond];
				indexSecond++;
			}

			indexMerged++;
		}

		int fillIndex = indexFirst + indexSecond;

		while (fillIndex < arr1.length + arr2.length) {
			if (indexFirst < arr1.length) {
				merged[indexMerged] = arr1[indexFirst];
				indexFirst++;
            } else {
				merged[indexMerged] = arr2[indexSecond];
				indexSecond++;
            }

            indexMerged++;
            fillIndex++;
		}

		return merged;
	}

	public static void main(String[] args) {
		MegaMergeSort mms = new MegaMergeSort();
		int[] arr = new int[] { 1, 2, 6, 7, 4, 3, 8, 9, 0, 5 };
		int[] res = mms.megaMergeSort(arr, 4);
		System.out.println(Arrays.toString(res));
	}
}
