package pgdp.collections;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class CountingObjectsCollection<K> {
	private HashMap<K, Integer> map;

	public CountingObjectsCollection() {
		map = new HashMap<>();
	}

	public CountingObjectsCollection(CountingObjectsCollection<K> old) {
		map = new HashMap<>(old.map);
	}

	public void insert(K obj) {
		map.compute(obj, (key, value) -> (value == null) ? 1 : value + 1);
	}

	public int getObjectCount(K obj) {
		return map.getOrDefault(obj, 0);
		// return map.containsKey(obj) ? map.get(obj) : 0;
	}

	public int getTotalObjectCount() {
		int objCount = 0;

		for (K key : map.keySet()) {
			objCount += this.getObjectCount(key);
		}

		return objCount;
	}

	public List<K> getKeyList() {
		return new ArrayList<K>(map.keySet());
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}

		CountingObjectsCollection<K> other = (CountingObjectsCollection<K>) obj;

		if (map.size() != other.map.size()) {
			return false;
		}

		List<K> keyListOther = other.getKeyList();
		int counter = 0;

		for (K key : map.keySet()) {
			if (!map.get(key).equals(other.map.get(keyListOther.get(counter)))) {
				return false;
			}

			counter++;
		}

		return true;
	}

	@Override
	public String toString() {
		StringBuilder out = new StringBuilder("[");

		for (K key : map.keySet()) {
			out.append(key.toString()).append(" -> ").append(map.get(key).toString());

			if (!map.keySet().stream().toList().get(map.size() - 1).equals(key)) {
				out.append("; ");
			}
		}

		out.append("]");
		return out.toString();
	}

	public static void main(String[] args) {
		CountingObjectsCollection<String> c1 = new CountingObjectsCollection<>();
		CountingObjectsCollection<String> c2 = new CountingObjectsCollection<>();
		HashMap<String, Integer> m1 = (HashMap<String, Integer>) c1.map;
		HashMap<String, Integer> m2 = (HashMap<String, Integer>) c2.map;
		String s1 = "Hello World";

		// Check empty collections
		System.out.println(c1.equals(c2));

		// Insert String
		m1.put(s1, 1);

			// Intermediate check
			System.out.println(c1.equals(c2));

		m2.put(s1, 2);

		m1.put(s1, 2);

		// Print collections
		System.out.println(c1);
		System.out.println(c2);

		// Check filled collections
		System.out.println(c1.equals(c2));
	}
}
