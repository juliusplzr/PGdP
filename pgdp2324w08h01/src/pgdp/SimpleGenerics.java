package pgdp;

import java.lang.reflect.Array;
import java.util.*;

public final class SimpleGenerics {

	private SimpleGenerics() {
		throw new UnsupportedOperationException();
	}

	/**
	 * Returns a String of the given Collection.
	 * 
	 * @param collection
	 * @return String representation of the collection
	 */
	public static String toString(Collection<?> collection) {
		if (collection.isEmpty()) {
			return "{}";
		}

		final StringBuilder out = new StringBuilder();
		out.append("{");

        for (final var e : collection) {
			out.append(e.toString()).append(", ");
		}

		return out.substring(0, out.lastIndexOf(",")) + "}";
	}

	/**
	 * Returns int array of collection.
	 * 
	 * @param collection
	 * @return int array
	 */
	public static int[] toIntArray(Collection<Integer> collection) {
		if (collection.isEmpty()) {
			return new int[] {};
		}

		final int[] intArray = new int[collection.size()];

		int index = 0;

		for (final var e : collection) {
			intArray[index] = e;
			index++;
		}

		return intArray;
	}

	/**
	 * Generates a generic array of type T with the given length.
	 * 
	 * @param <T>
	 * @param clazz
	 * @param length
	 * @return reference to the generated generic array
	 */
	@SuppressWarnings("unchecked")
	public static <T> T[] generateGenericArray(Class<T> clazz, int length) {
		final T[] arr = (T[]) Array.newInstance(clazz, length);
		return arr;
	}

	/**
	 * Returns the given collection in a sorted array.
	 * 
	 * @param <T>
	 * @param clazz
	 * @param collection
	 * @param comparator dictates the order of the output
	 * @return array of type T
	 */
	public static <T> T[] specialSort(Class<T> clazz, Collection<T> collection, Comparator<T> comparator) {
		if (collection.isEmpty()) {
			return generateGenericArray(clazz, 0);
		}

		final T[] toSort = generateGenericArray(clazz, collection.size());

		int index = 0;

		for (final var e : collection) {
			toSort[index] = e;
			index++;
		}

		mergeSort(clazz, toSort, 0, toSort.length - 1, comparator);

		return toSort;
	}

	// <========================= Merge Sort ===========================>
	private static <T> void mergeSort(Class<T> clazz, T[] unsorted, int start, int end, Comparator<T> comparator) {
		if (start < end) {
			int middle = start + (end - start) / 2;
			mergeSort(clazz, unsorted, start, middle, comparator);
			mergeSort(clazz, unsorted , middle + 1, end, comparator);
			merge(clazz, unsorted, start, middle, end, comparator);
		}
	}

	public static <T> void merge(Class<T> clazz, T[] arr, int start, int middle, int end, Comparator<T> comparator) {
		int n1 = middle - start + 1;
		int n2 = end - middle;

		T[] left = generateGenericArray(clazz, n1);
		T[] right = generateGenericArray(clazz, n1);

	        if (n1 >= 0) {
			System.arraycopy(arr, start, left, 0, n1);
		}

		for (int i = 0; i < n2; i++) {
			right[i] = arr[middle + 1 + i];
		}

		int i = 0;
		int j = 0;
		int k = start;

		while (i < n1 && j < n2) {
			if (comparator.compare(left[i], right[j]) < 0) {
				arr[k++] = left[i++];
			} else {
				arr[k++] = right[j++];
			}
		}

		while (i < n1) {
			arr[k++] = left[i++];
		}

		while (j < n2) {
			arr[k++] = right[j++];
		}
	}

	/**
	 * Returns a collection of all elements that are contained by each Collection of
	 * collections. Collections of the input are not modified.
	 * 
	 * @param <T>
	 * @param collections not null, may not contain null values.
	 * @return intersection of all collections
	 */
	public static <T> Collection<T> intersection(Collection<T>[] collections) {
		if (collections.length == 0) {
			return new ArrayList<>();
		}

		for (final Collection<T> collection : collections) {
			if (collection.isEmpty()) {
				return new ArrayList<>();
			}
		}

		final List<T> intersection = new ArrayList<>();

		int current = 0;

		while (current < collections.length) {
			if (collections.length == 1) {
				return collections[current];
			}

			A:
			for (final var e : collections[current]) {
				boolean includedAll = true;

				B:
				for (final Collection<T> collection : collections) {
					boolean includedInThis = false;

					C:
					for (final var t : collection) {
						if (e.equals(t)) {
							includedInThis = true;
							break;
						}
					}

					if (!includedInThis) {
						includedAll = false;
						break;
					}
				}

				if (includedAll) {
					intersection.add(e);
				}
			}

			current++;
		}

		Set<T> setWithoutDuplicates = new LinkedHashSet<>(intersection);

        return new ArrayList<>(setWithoutDuplicates);
	}

	/**
	 * Returns the values stored in the map. Equivalent to map.values().
	 * 
	 * @param <K> key type
	 * @param <V> value type
	 * @param map
	 * @return set of values
	 */
	public static <K, V> Set<V> getValues(Map<K, V> map) {
		final Set<V> values = new HashSet<>();

		for (final Map.Entry<K, V> entry : map.entrySet()) {
			values.add(entry.getValue());
		}

		return values;
	}

	//@SuppressWarnings("unchecked")
	public static void main(String... args) {
		// toString
		/*
		System.out.println("<=============== toString Test ===============> \n");
		List<?> l = Arrays.asList(1, 2, 3, 5, 4);
		System.out.println(toString(l));

		Integer[] values1 = { -4, -1, 0, 4, 9, 16, 17 };
		Set<Integer> set1 = Set.of(values1);

		System.out.println(toString(set1) + "\n");

		// toIntArray
		System.out.println("<=============== toIntArray Test ===============> \n");
		List<Integer> l1 = Arrays.asList(1, 2, 3, 5, 4);
		int[] a = toIntArray(l1);

		System.out.println(Arrays.toString(a) + "\n");

		// intersection
		System.out.println("<=============== intersection Test ===============> \n");
		Integer[] values2 = { 1, -8, -4, 3, -5, 6, -9 };
		Set<Integer> set2 = Set.of(values2);
		Collection<Integer>[] collections1 = new Collection[] {set1, set2};
		Collection<Integer>[] collections2 = new Collection[] {set2};
		Collection<Integer>[] collections3 = new Collection[] {set1};

		System.out.println(toString(intersection(collections1)));
		System.out.println(toString(intersection(collections2)));
		System.out.println(toString(intersection(collections3)));
		*/
	}
}
