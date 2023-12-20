package exercises;

import java.util.Arrays;

public class SortingAlgorithms {
    public static void selectionSortRec(int[] array) {
        selectionSortRecTo(array, array.length - 1);
    }

    public static void selectionSortRecTo(int[] array, int to) {
        if (to == 0) {
            return;
        }

        int maxIdx = 0;

        for (int i = 0; i <= to; i++) {
            if (array[i] > array[maxIdx]) {
                maxIdx = i;
            }
        }

        int temp = array[maxIdx];
        array[maxIdx] = array[to];
        array[to] = temp;

        selectionSortRecTo(array, to - 1);
    }
}
