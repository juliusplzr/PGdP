package pgdp.ds;

import java.util.Arrays;

public class RingBuffer {

	private int[] mem;
	private int in;
	private int out;
	private int stored;

	RingBuffer(int capacity) {
		mem = new int[capacity];
		in = 0;
		out = 0;
		stored = 0;
	}

	public boolean isEmpty() {
		return stored == 0;
	}

	public boolean isFull() {
		return stored == mem.length;
	}

	public boolean put(int element) {
		if (isFull()) {
			return false;
		}

		mem[in] = element;
		in++;
		in %= mem.length;
		stored++;

		return true;
	}

	public int get() {
		if (isEmpty()) {
			return Integer.MIN_VALUE;
		}

		int result = mem[out];
		out++;
		out %= mem.length;
		stored--;

		return result;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("RingBuffer := { capacity = ").append(mem.length).append(", out = ").append(out).append(", in = ")
				.append(in).append(", stored = ").append(stored).append(", mem = ").append(Arrays.toString(mem))
				.append(", buffer = [");
		if (!isEmpty()) {
			if (in >= 0 || in < mem.length) {
				int i = out;
				do {
					sb.append(mem[i]).append(", ");
					i = (i + 1) % mem.length;
				} while (i != in);
				sb.setLength(sb.length() - 2);
			} else {
				sb.append("Error: Field 'in' is <").append(in).append(">, which is out of bounds for an array of length ").append(mem.length);
			}
		}
		sb.append("] }");
		return sb.toString();
	}

	public void setOut(int out) {
		this.out = out;
	}

	public void setIn(int in) {
		this.in = in;
	}

	public static void main(String[] args) {
		RingBuffer rb = new RingBuffer(10);
		rb.put(1);
		System.out.println(rb.toString());
	}
}
