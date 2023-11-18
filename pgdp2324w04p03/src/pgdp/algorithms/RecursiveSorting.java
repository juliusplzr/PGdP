package pgdp.algorithms;

import java.util.Arrays;

public class RecursiveSorting {

    /**	Implementation of the MergeSort algorithm
     *
     * @param array Any Integer-Array
     * @return The passed Integer-Array, but sorted in ascending order
     */
    public static int[] mergeSort(int[] array) {
        if (array.length <= 1) {
            return array;
        }

        int mid = array.length/2;

        int[] left = new int[mid];
        int[] right = new int[array.length - mid];

        for (int i = 0; i < mid; i++) {
            left[i] = array[i];
        }

        for (int i = mid; i < array.length; i++) {
            right[i - mid] = array[i];
        }

        left = mergeSort(left);
        right = mergeSort(right);

        return merge(left, right);
    }

    // Hilfsmethode (muss nicht verwendet werden, könnte aber hilfreich sein)
    public static int[] merge(int[] first, int[] second) {
        int[] merged = new int[first.length + second.length];

        int index = 0;

        int firstIndex = 0;
        int secondIndex = 0;

        while (firstIndex < first.length && secondIndex < second.length) {
            if (first[firstIndex] <= second[secondIndex]) {
                merged[index] = first[firstIndex];
                firstIndex++;
            } else {
                merged[index] = second[secondIndex];
                secondIndex++;
            }

            index++;
        }

        while (firstIndex < first.length) {
            merged[index] = first[firstIndex];
            firstIndex++;
            index++;
        }

        while (secondIndex < second.length) {
            merged[index] = second[secondIndex];
            secondIndex++;
            index++;
        }

        return merged;
    }



    /**	Implementation of the StoogeSort algorithm
     *
     * @param array Any Integer-Array
     * @return The passed Integer-Array, but sorted in ascending order
     */
    public static void stoogeSort(int[] array) {
        stoogeSort(array, 0, array.length);
    }

    public static void stoogeSort(int[] array, int from, int to) {
        int length = to - from;

        if (length == 2 && array[from] > array[from + 1]) {
            swap(array, from, from + 1);
        }

        if (length <= 2) {
            return;
        }

        int rem = length % 3;

        int left = from + length / 3;
        int mid = length / 3 + rem;

        stoogeSort(array, from, left + mid);
        stoogeSort(array, left, to);
        stoogeSort(array, from, left + mid);
    }



    /**	Implementation of the SelectionSort algorithm in a recursive way
     *
     * @param a Any Integer-Array
     * @return The passed Integer-Array, but sorted in ascending order
     */
    public static void selectionSortRec(int[] a) {
        selectionSortRec(a, a.length - 1);
    }

    public static void selectionSortRec(int[] a, int toIncl) {
        if (toIncl <= 0) {
            return;
        }

        int index = findIndexOfLargest(a, toIncl);
        swap(a, toIncl, index);

        selectionSortRec(a, toIncl - 1);
    }

    // Hilfsmethode (muss nicht verwendet werden, könnte aber hilfreich sein)
    public static int findIndexOfLargest(int[] a, int toIncl) {
        if (toIncl == 0) {
           return 0;
        }

        int maxIndex = 0;

        for (int i = 0; i <= toIncl; i++) {
            if (a[i] >= a[maxIndex]) {
                maxIndex = i;
            }
        }

        return maxIndex;
    }

    // Hilfsmethode (muss nicht verwendet werden, könnte aber hilfreich sein)
    public static void swap(int[] a, int firstPos, int secondPos) {
        int temp = a[secondPos];
        a[secondPos] = a[firstPos];
        a[firstPos] = temp;
    }

    // Für Experimente
    public static void main(String[] args) {
        int[] a = {3, 4, 9, 2, 5, 0, 2, 1, 6, 4, -3};
        selectionSortRec(a);
        System.out.println(Arrays.toString(a));
    }

}
