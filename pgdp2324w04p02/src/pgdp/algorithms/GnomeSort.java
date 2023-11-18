package pgdp.algorithms;

import java.util.Arrays;

public class GnomeSort {

	/**
	 * Sort input array using gnome sort
	 * @param a integer array to be sorted using gnome sort
	 */
	public static void gnomeSort(int[] a) {
		int index = 0;

		while (index < a.length) {
			int current = a[index];
			if (index == 0 || current >= a[index - 1]) {
				index++;
			} else {
				int temp = a[index - 1];
				a[index - 1] = current;
				a[index] = temp;
				index--;
			}
		}
	}

	public static void main(String[] args) {
		int[] a = new int[] { 5, 2, 3, 1, 6, 0, 4 };
		gnomeSort(a);
		System.out.println(Arrays.toString(a));
	}

}
