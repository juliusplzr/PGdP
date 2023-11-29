package pgdp.datastructures.lists;

public class RecIntListElement {
	private int value;
	private RecIntListElement next;
	private RecIntListElement prev;

	public RecIntListElement(int value) {
		this(value, null);
	}

	public RecIntListElement(int value, RecIntListElement prev) {
		this.value = value;
		next = null;
		this.prev = prev;
	}

	public RecIntListElement append(int value) {
		if (next != null) {
			return next.append(value);
		} else {
			next = new RecIntListElement(value, this);
			return next;
		}
	}

	public int getValue() {
		return value;
	}

	public int get(int idx) {
		if (idx == 0) {
			return value;
		}
		if (next == null) {
			System.out.println("Invalid index: list is to short!");
			return Integer.MIN_VALUE;
		}
		return next.get(idx - 1);
	}

	public int size() {
		if (next == null) {
			return 1;
		}
		return 1 + next.size();
	}

	public boolean insert(int value, int idx) {
		if (idx < 0) {
			System.out.println("Cannot insert at negative index!");
			return false;
		}
		if (idx <= 1) {
			RecIntListElement n = new RecIntListElement(value, this);
			n.next = next;
			if (next != null) {
				next.prev = n;
			}
			next = n;
			if (idx == 0) {
				next.value = this.value;
				this.value = value;
			}
			return true;
		}
		if (next == null) {
			System.out.println("List is to short to insert at given index!");
			return false;
		}
		return next.insert(value, idx - 1);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		RecIntListElement tmp = this;
		do {
			sb.append(tmp.value).append(", ");
			tmp = tmp.next;
		} while (tmp != null);
		sb.setLength(sb.length() - 2);
		return sb.toString();
	}

	public String toConnectionString() {
		StringBuilder sb = new StringBuilder();
		RecIntListElement tmp = this;
		do {
			if (tmp.prev != null) {
				sb.append("<-");
			}
			sb.append(tmp.value);
			if (tmp.next != null) {
				sb.append("->");
			}
			tmp = tmp.next;
		} while (tmp != null);
		return sb.toString();
	}

	public long[] countTresh(int threshold) {
		long[] init = new long[3];

		countTreshRec(threshold, init);

		return init;
	}

	public void countTreshRec(int threshold, long[] result) {
		if (this.value > threshold) {
			result[2] += this.value;
		} else if (this.value < threshold) {
			result[0] += this.value;
		} else {
			result[1] += this.value;
		}

		if (this.next == null) {
			return;
		} else {
			this.next.countTreshRec(threshold, result);
		}
	}

	public void kinguinSort(boolean increasing) {
		if (this.next == null) {
			return;
		}

		if ((increasing && this.value > this.next.value) || (!(increasing) && this.value < this.next.value)) {
			this.next = this.next.next;
			if (this.next != null) {
				this.next.prev = this;
				this.kinguinSort(increasing);
			}
		} else {
			next.kinguinSort(increasing);
		}
	}

	public RecIntListElement reverse() {
		return this.reverseRec(null);
	}

	public RecIntListElement reverseRec(RecIntListElement prev) {
		RecIntListElement temp = this.next;
		this.next = prev;
		this.prev = temp;

		if (temp == null) {
			return this;
		}

		return temp.reverseRec(this);
	}

	public static void zip(RecIntListElement l1, RecIntListElement l2) {
		zipRec(l1, l2, l1.next);
	}

	public static void zipRec(RecIntListElement l, RecIntListElement next1, RecIntListElement next2) {
		if (next1 == null) {
			if (next2 == null) {
				return;
			}
			l.next = next2;
			next2.prev = l;
			return;
		}

		if (next2 == null) {
			l.next = next1;
			next1.prev = l;
			return;
		}

		l.next = next1;
		next1.prev = l;
		zipRec(l.next, next2, next1.next);
	}
}
