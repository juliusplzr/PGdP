package pgdp.pingulib.datastructures.lists;

public class List {

	private Element head;
	private Element tail;
	private int size;

	public List() {
		this.head = null;
		this.tail = null;
		this.size = 0;
	}

	/*
	 * returns size/length of the list
	 */
	public int size() {
		return this.size;
	}

	/*
	 * returns <true> if the list is empty, otherwise <false>
	 */
	public boolean isEmpty() {
		// TODO
		return this.size == 0;
	}

	/*
	 * removes all elements from the list
	 */
	public void clear() {
		this.head = null;
		this.tail = null;
		this.size = 0;
	}

	/*
	 * adds an element at the end of the list
	 */
	public void add(int element) {
		if (this.isEmpty()) {
			this.head = new Element(element);
			this.tail = this.head;
			this.size++;
			return;
		}

		this.tail.next = new Element(element);
		this.tail = this.tail.next;
		this.size++;
	}

	/*
	 * adds an element at the specified index
	 */
	public boolean add(int index, int element) {
		if (index < 0 || index > this.size) {
			return false;
		}

		Element current = this.head;
		if (isEmpty()) {
			this.head = new Element(element);
			this.tail = this.head;
		} else if (index == 0) {
			this.head = new Element(element, this.head);
		} else if (index == size) {
			tail.next = new Element(element);
			this.tail = this.tail.next;
		} else {
			for (int i = 0; i < index - 1; i++) {
				current = current.next;
			}

			current.next = new Element(element, current.next);
		}
		this.size++;
		return true;
	}

	/*
	 * returns the value of the element at the specified index returns default value
	 * (minimum value of an integer) iff. such an element does not exist.
	 */
	public int get(int index) {
		if (this.isEmpty()) {
			return Integer.MIN_VALUE;
		} else {
			Element current = this.head;
			for (int i = 0; i < index; i++) {
				current = current.next;
			}

			return current != null ? current.value : Integer.MIN_VALUE;
		}
	}

	/*
	 * removes the element at the specified index
	 */
	public void remove(int index) {
		if (index < 0 || index >= size || this.isEmpty()) {
			return;
		} else if (index == 0) {
			this.head = this.head.next;
		} else if (this.size == 1) {
			this.head = null;
			this.tail = null;
		} else {
			Element current = this.head;

			for (int i = 0; i < index - 1; i++) {
				current = current.next;
			}

			if (index == size - 1) {
				this.tail = current;
			} else {
				current.next = current.next.next;
			}
		}
		this.size--;
	}

	/*
	 * returns String representation of the list
	 */
	@Override
	public String toString() {
		StringBuilder out = new StringBuilder();
		out.append("[ ");
		Element current = head;
		for (int i = 0; i < size; i++) {
			out.append(current.toString());
			if (i != size - 1) {
				out.append(", ");
			}
			current = current.next;
		}
		out.append(" ]");
		return out.toString();
	}

	private static class Element {
		private int value;
		private Element next;

		Element(int value) {
			this.value = value;
			this.next = null;
		}

		Element(int value, Element next) {
			this.value = value;
			this.next = next;
		}

		/*
		 * returns String representation of the element
		 */
		@Override
		public String toString() {
			return "" + value;
		}
	}

	public static void main(String[] args) {
		Element element5 = new Element(5);
		Element element4 = new Element(4, element5);
		Element element3 = new Element(2, element4);
		Element element2 = new Element(-1, element3);
		Element element1 = new Element(0, element2);

		List list = new List();

		list.add(0, element1.value);
		list.add(1, element2.value);
		list.add(2, element3.value);
		list.add(3, element4.value);
		list.add(4, element5.value);

		System.out.println(list.toString());
		System.out.println("Head element: " + list.get(0));

		System.out.println("\nRemove Head element: ");
		list.remove(0);
		System.out.println(list.toString());
	}

}